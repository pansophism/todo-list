package com.mashape.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.common.AppConfig;
import com.mashape.domain.Task;
import com.mashape.interfaces.TaskDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/17/14.
 */
public class TaskDaoMongoImpl implements TaskDao {

    private final AppConfig config = AppConfig.getInstance();

    private static final String COLLECTIONNAME = "task";
    private final ObjectMapper mapper = new ObjectMapper();
    private final int port = config.getInt("", 27017);

    private final DB mongoDB;

    public TaskDaoMongoImpl() throws UnknownHostException {
        MongoClient mongoClient;
        mongoClient = new MongoClient("localhost", port);
        mongoDB = mongoClient.getDB("task");
    }

    @Override
    public Iterable<Task> getAll() throws IOException {
        return null;
    }

    @Override
    public final Task get(final long id) throws IOException {
        BasicDBObject query = new BasicDBObject("id", id);
        DBObject obj = mongoDB.getCollection(COLLECTIONNAME).findOne(query);
        return mapper.readValue(obj.toString(), Task.class);
    }

    @Override
    public final boolean update(final Task task) {
        return false;
    }

    @Override
    public final boolean insert(final Task task) throws IOException {
        Task oldObj = get(task.getTaskId());
        BasicDBObject dbObject = new BasicDBObject("id", task.getTaskId());
        return false;
    }

    @Override
    public final boolean delete(final long id) {
        BasicDBObject dbObject = new BasicDBObject("id", id);
        mongoDB.getCollection(COLLECTIONNAME).remove(dbObject);
        return false;
    }

    @Override
    public final boolean delete(final Task task) {
        BasicDBObject dbObject = new BasicDBObject("id", task.getTaskId());
        mongoDB.getCollection(COLLECTIONNAME).remove(dbObject);
        return false;
    }
}
