package com.mashape.dao;

import com.google.inject.Inject;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.config.AppConfig;
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

    private final Indexer indexer;
    private final NotificationService notificationService;

    @Inject
    public TaskDaoWithIndexingImpl(
            MongoClient mongo,
            TaskToMongoObjMapper mapper,
            Indexer indexer,
            NotificationService notificationService) {

        super(mongo, mapper);
        this.indexer = indexer;
        this.notificationService = notificationService;
    }

    @Override
    public Task insert(Task task) throws Exception {
        Task aTask = super.insert(task);
        indexer.index(task);

        return aTask;
    }

    @Override
    public boolean update(Task task) throws Exception {

        if(task == null || task.getId() == null) {
            throw new NotUpdatableException("Task cannot be updated : " + task.toString());
        }

        try {
            Task oldTask = get(task.getId());

            if(AppConfig.getInstance().getBoolean("notification.enable.key", false)
                    && task.isDone()
                    && !oldTask.isDone()) {

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
    public void delete(String id) throws Exception {
        super.delete(id);
        indexer.delete(id);
    }
}
