package com.mashape.dao;

import com.google.inject.Inject;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Message;
import com.mashape.domain.Task;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.Indexer;
import com.mashape.interfaces.NotificationService;
import com.mongodb.MongoClient;
import com.twilio.sdk.TwilioRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yxzhao on 10/20/14.
 */
public class TaskDaoWithNotificationImpl extends TaskDaoWithIndexingImpl {

    private static final Logger LOG = LoggerFactory.getLogger(TaskDaoWithNotificationImpl.class);

    private NotificationService notificationService;

    public TaskDaoWithNotificationImpl(MongoClient mongo, TaskToMongoObjMapper mapper, Indexer indexer) {
        super(mongo, mapper, indexer);
    }

    @Override
    public boolean update(final Task task) throws Exception {

        boolean result = false;

//        try {
//            Task oldTask = get(task.getId());
//            result = super.update(task);
//
//            if(!oldTask.isDone()) {
//                Task newTask = get(task.getId());
//                if(newTask.isDone()) {
//                    notificationService.notify(new Message("+14152269668", "Task done : " + task));
//                }
//            }
//        } catch (TwilioRestException e) {
//            LOG.error("TwilioRestException : ", e);
//        } catch (TaskNotFoundException e) {
//            LOG.error("TaskNotFoundException : ", e);
//            throw new NotUpdatableException("Task cannot be updated : " + task.toString() + " ERROR : " + e.getMessage());
//        }

        return result;

    }
}
