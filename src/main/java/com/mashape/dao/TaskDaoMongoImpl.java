package com.mashape.dao;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Task;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.TaskDao;
import com.mongodb.*;
import org.bson.types.ObjectId;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

/**
 * Created by yxzhao on 10/17/14.
 */

@Singleton
public class TaskDaoMongoImpl implements TaskDao {

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
    Iterable<Task> getAll() throws IOException {
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
    public final Task get(final String id) throws IOException, TaskNotFoundException {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);

        if(data == null) {
            throw new TaskNotFoundException("No task can be retrieved using the id you offered: : " + id);
        }

        return mapper.toTask(data);
    }

    @Override
    public final boolean update(final Task task) throws NotUpdatableException {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(task.getTaskId())).get();
        WriteResult result = this.collection.update(query, mapper.toDBObject(task));
        boolean updated = result.isUpdateOfExisting();

        if (!updated) {
            throw new NotUpdatableException("Task cannot be updated : " + task.toString());
        }

        return true;
    }

    @Override
    public final Task insert(final Task task) throws IOException {

        DBObject doc = mapper.toDBObject(task);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        task.setTaskId(id.toString());

        return task;
    }

    @Override
    public final void delete(final String id) throws IOException, TaskNotFoundException {
        get(id);

        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        this.collection.remove(query);
    }

    @Override
    public final void delete(final Task task) throws IOException, TaskNotFoundException {
        delete(task.getTaskId());
    }
}
