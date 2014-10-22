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

    private static final String IDKEY = "_id";

    public final DBObject toDBObject(final Task task) {

        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append(Constants.Fileds.CONTENT.value(), task.getContent())
                .append(Constants.Fileds.TITLE.value(), task.getTitle())
                .append(Constants.Fileds.DONE.value(), task.isDone());

        if (!Strings.isNullOrEmpty(task.getId())) {
            builder = builder.append(IDKEY, new ObjectId(String.valueOf(task.getId())));
        }

        return builder.get();
    }

    public final Task toTask(final DBObject doc) {
        ObjectId objectId = (ObjectId) doc.get(IDKEY);
        Object isDoneField = doc.get(Constants.Fileds.DONE.value());


        boolean isDone = false;
        if (isDoneField != null) {
            isDone = Boolean.valueOf(isDoneField.toString());
        }

        Task task = new Task(
                objectId.toString(),
                doc.get(Constants.Fileds.TITLE.value()).toString(),
                doc.get(Constants.Fileds.CONTENT.value()).toString(),
                isDone);

        return task;

    }
}
