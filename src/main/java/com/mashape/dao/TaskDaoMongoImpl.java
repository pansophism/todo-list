package com.mashape.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Task;
import com.mashape.exception.CannotInsertException;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.TaskDao;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by yxzhao on 10/17/14.
 */

@Singleton
public class TaskDaoMongoImpl implements TaskDao {

    private static final Logger LOG
            = LoggerFactory.getLogger(TaskDaoMongoImpl.class);

    protected DBCollection collection;
    protected TaskToMongoObjMapper mapper;

    @Inject
    public TaskDaoMongoImpl(final MongoClient mongo, final TaskToMongoObjMapper mapper) {
        this.collection = mongo.getDB("todo-list").getCollection("task");
        this.mapper = mapper;
    }

    @Override
    public final @Nonnull Iterable<Task> getAll() {
        List<Task> data = Lists.newArrayList();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Task aTask = mapper.toTask(doc);
            data.add(aTask);
        }

        return data;
    }

    @Override
    public final Task get(final String id) throws TaskNotFoundException {

        DBObject query;
        DBObject data = null;

        try {
            query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
            data = this.collection.findOne(query);

        } catch (Exception e) {
            LOG.error("Exception while querying task : " + id, e);
        }

        if (data == null) {
            throw new TaskNotFoundException(
                    "No task can be retrieved using the id you offered: : " + id);
        }

        return mapper.toTask(data);
    }

    @Override
    public boolean update(final Task task) throws Exception {

        if (task == null || Strings.isNullOrEmpty(task.getId())) {
            throw new NotUpdatableException("Task cannot be updated : " + task);
        }

        try {
            get(task.getId());

            DBObject query = BasicDBObjectBuilder
                    .start().append("_id", new ObjectId(task.getId())).get();

            WriteResult result = this.collection.update(query, mapper.toDBObject(task));
            result.isUpdateOfExisting();
        } catch (Exception e) {
            LOG.error("Exception while updating task " + task, e);
            throw new NotUpdatableException(
                    "Task cannot be updated : " + task.toString() + " ERROR : " + e.getMessage());
        }

        return true;
    }

    @Override
    public Task insert(final Task task) throws Exception {

        try {
            DBObject doc = mapper.toDBObject(task);
            this.collection.insert(doc);
            ObjectId id = (ObjectId) doc.get("_id");
            task.setId(id.toString());
        } catch (Exception e) {
            LOG.error("Exception while inserting task : " + task, e);

            throw new CannotInsertException("Task cannot be inserted : : " + task);
        }

        return task;
    }

    @Override
    public void delete(final String id) throws Exception {
        get(id);

        DBObject query =
                BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();

        this.collection.remove(query);
    }

}
