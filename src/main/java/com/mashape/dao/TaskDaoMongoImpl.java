package com.mashape.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  private final DB mongoDB;

  private final String collectionName = "task";
  private final ObjectMapper mapper = new ObjectMapper();

  public TaskDaoMongoImpl() throws UnknownHostException
  {
    MongoClient mongoClient;
    mongoClient = new MongoClient("localhost" , 27017 );
    mongoDB = mongoClient.getDB("task");
  }

  @Override
  public Task get(long id) throws IOException
  {
    BasicDBObject query = new BasicDBObject("id", id);
    DBObject obj = mongoDB.getCollection(collectionName).findOne(query);
    return mapper.readValue(obj.toString(), Task.class);
  }

  @Override
  public boolean update(Task task)
  {
    return false;
  }

  @Override
  public boolean insert(Task task) throws IOException
  {
    Task oldObj = get(task.getTaskId());
    BasicDBObject dbObject = new BasicDBObject("id", task.getTaskId());
//    mongoDB.getCollection(collectionName).update(dbObject, mapper.writeValueAsString(task));
    return false;
  }

  @Override
  public boolean delete(long id)
  {
    BasicDBObject dbObject = new BasicDBObject("id", id);
    mongoDB.getCollection(collectionName).remove(dbObject);
    return false;
  }

  @Override
  public boolean delete(Task task)
  {
    BasicDBObject dbObject = new BasicDBObject("id", task.getTaskId());
    mongoDB.getCollection(collectionName).remove(dbObject);
    return false;
  }
}
