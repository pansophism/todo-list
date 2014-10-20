package com.mashape.common;

import com.google.common.base.Strings;
import com.mashape.domain.Task;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskToMongoObjMapper {

    public DBObject toDBObject(Task task) {

        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("content", task.getContent())
                .append("title", task.getTitle())
                .append("isDone", task.isDone());

        if (!Strings.isNullOrEmpty(task.getTaskId())) {
            builder = builder.append("_id", new ObjectId(String.valueOf(task.getTaskId())));
        }

        return builder.get();
    }

    public Task toTask(DBObject doc) {
        ObjectId objectId = (ObjectId) doc.get("_id");
        Task task = new Task(
                objectId.toString(),
                doc.get("title").toString(),
                doc.get("content").toString(),
                doc.get("isDone") != null ? Boolean.valueOf(doc.get("isDone").toString()) : false
        );

        return task;

    }
}
