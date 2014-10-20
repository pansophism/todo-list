package com.mashape.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoMongoImpl;
import com.mashape.interfaces.TaskDao;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskModule extends AbstractModule {

    @Provides
    TaskToMongoObjMapper providesMapper() {
        return new TaskToMongoObjMapper();
    }

    @Provides
    MongoClient provideMongoClient() throws UnknownHostException {
        return new MongoClient("localhost");
    }

    @Override
    protected void configure() {
        bind(TaskDao.class).to(TaskDaoMongoImpl.class);
    }
}
