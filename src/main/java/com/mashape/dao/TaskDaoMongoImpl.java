package com.mashape.dao;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Task;
import com.mashape.exception.CannotInsertException;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.TaskDao;
import com.mongodb.*;
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

    private static final Logger LOG = LoggerFactory.getLogger(TaskDaoMongoImpl.class);

    private final DBCollection collection;
    private final TaskToMongoObjMapper mapper;

    @Inject
    public TaskDaoMongoImpl(MongoClient mongo, TaskToMongoObjMapper mapper) {
        this.collection = mongo.getDB("todo-list").getCollection("task");
        this.mapper = mapper;
    }

    @Override
    public
    @Nonnull
    Iterable<Task> getAll() {
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
            throw new TaskNotFoundException("No task can be retrieved using the id you offered: : " + id);
        }

        return mapper.toTask(data);
    }

    @Override
    public boolean update(final Task task) throws NotUpdatableException {

        if(task == null || task.getTaskId() == null) {
            throw new NotUpdatableException("Task cannot be updated : " + task.toString());
        }

        try {
            get(task.getTaskId());

            DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(task.getTaskId())).get();
            WriteResult result = this.collection.update(query, mapper.toDBObject(task));
            result.isUpdateOfExisting();
        } catch (Exception e) {
            LOG.error("Exception while updating task " + task, e);
            throw new NotUpdatableException("Task cannot be updated : " + task.toString() + " ERROR : " + e.getMessage());
        }

        return true;
    }

    @Override
    public final Task insert(final Task task) throws CannotInsertException {

        try {
            DBObject doc = mapper.toDBObject(task);
            this.collection.insert(doc);
            ObjectId id = (ObjectId) doc.get("_id");
            task.setTaskId(id.toString());
        } catch (Exception e) {
            LOG.error("Exception while inserting task : " + task, e);

            throw new CannotInsertException("Task cannot be inserted : : " + task);
        }

        return task;
    }

    @Override
    public final void delete(final String id) throws TaskNotFoundException {
        get(id);

        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        this.collection.remove(query);
    }

    @Override
    public final void delete(final Task task) throws TaskNotFoundException {
        if(task == null || task.getTaskId() == null) {
           throw new TaskNotFoundException("No such task : " + task);
        }
        get(task.getTaskId());
        delete(task.getTaskId());
    }
}
