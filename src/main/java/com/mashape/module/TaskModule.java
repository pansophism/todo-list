package com.mashape.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mashape.common.AppConfig;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoWithIndexingImpl;
import com.mashape.interfaces.TaskDao;
import com.mongodb.MongoClient;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskModule extends AbstractModule {

    private static AppConfig CONFIG = AppConfig.getInstance();

    @Provides
    TaskToMongoObjMapper providesMapper() {
        return new TaskToMongoObjMapper();
    }

    @Provides
    MongoClient provideMongoClient() throws UnknownHostException {
        return new MongoClient(
                CONFIG.getString("mongodb.server"),
                CONFIG.getInt("mongodb.port"));
    }

    @Override
    protected void configure() {
        bind(TaskDao.class).to(TaskDaoWithIndexingImpl.class).in(Singleton.class);
    }
}
