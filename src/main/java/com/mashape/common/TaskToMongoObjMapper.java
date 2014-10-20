package com.mashape.common;

import com.google.common.base.Strings;
import com.mashape.domain.Task;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.bson.types.ObjectId;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskToMongoObjMapper {

    private static final String IDKEY = "_id";

    public DBObject toDBObject(Task task) {

        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append(Constants.Fileds.CONTENT.value(), task.getContent())
                .append(Constants.Fileds.TITLE.value(), task.getTitle())
                .append(Constants.Fileds.DONE.value(), task.isDone());

        if (!Strings.isNullOrEmpty(task.getTaskId())) {
            builder = builder.append(IDKEY, new ObjectId(String.valueOf(task.getTaskId())));
        }

        return builder.get();
    }

    public Task toTask(DBObject doc) {
        ObjectId objectId = (ObjectId) doc.get(IDKEY);
        Object isDoneField = doc.get(Constants.Fileds.DONE.value());

        Task task = new Task(
                objectId.toString(),
                doc.get(Constants.Fileds.TITLE.value()).toString(),
                doc.get(Constants.Fileds.CONTENT.value()).toString(),
                isDoneField != null ? Boolean.valueOf(isDoneField.toString()) : false
        );

        return task;

    }
}
