package com.mashape.dao;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.mashape.common.AppConfig;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Message;
import com.mashape.domain.Task;
import com.mashape.exception.NotUpdatableException;
import com.mashape.interfaces.Indexer;
import com.mashape.interfaces.NotificationService;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yxzhao on 10/20/14.
 */
public class TaskDaoWithIndexingImpl extends TaskDaoMongoImpl {

    private static final Logger LOG = LoggerFactory.getLogger(TaskDaoWithIndexingImpl.class);

    boolean notify = AppConfig.getInstance().getBoolean("notification.enable.key", false);
    private final Indexer indexer;
    private final NotificationService notificationService;

    @Inject
    public TaskDaoWithIndexingImpl(
            final MongoClient mongo,
            final TaskToMongoObjMapper mapper,
            final Indexer indexer,
            final NotificationService notificationService) {

        super(mongo, mapper);
        this.indexer = indexer;
        this.notificationService = notificationService;
    }

    @Override
    public final Task insert(final Task task) throws Exception {
        Task aTask = super.insert(task);
        indexer.index(task);

        return aTask;
    }

    @Override
    public final boolean update(final Task task) throws Exception {

        if (task == null || Strings.isNullOrEmpty(task.getId())) {
            throw new NotUpdatableException("Task cannot be updated : " + task);
        }

        try {
            Task oldTask = get(task.getId());

            if (notify && task.isDone() && !oldTask.isDone()) {

                notificationService.notify(new Message("+14152269668", "Task done : " + task));
            }

            DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(task.getId())).get();
            WriteResult result = this.collection.update(query, mapper.toDBObject(task));
            result.isUpdateOfExisting();
        } catch (Exception e) {
            LOG.error("Exception while updating task " + task, e);
            throw new NotUpdatableException("Task cannot be updated : " + task.toString() + " ERROR : " + e.getMessage());
        }

        return true;
    }

    @Override
    public final void delete(final String id) throws Exception {
        super.delete(id);
        indexer.delete(id);
    }
}
