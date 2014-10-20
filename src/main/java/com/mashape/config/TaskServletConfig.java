package com.mashape.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoMongoImpl;
import com.mashape.interfaces.TaskDao;
import com.mongodb.MongoClient;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        final ResourceConfig rc = new PackagesResourceConfig("com.mashape.service");
        return Guice.createInjector(new JerseyServletModule() {

            @Provides
            TaskToMongoObjMapper providesMapper() {
                return new TaskToMongoObjMapper();
            }

            @Provides
            MongoClient provideMongoClient() throws UnknownHostException {
                return new MongoClient("localhost");
            }

            @Override
            protected void configureServlets() {
                super.configureServlets();

                bind(TaskDao.class).to(TaskDaoMongoImpl.class).in(Singleton.class);

                for (Class<?> resource : rc.getClasses()) {
                    bind(resource);
                }

                serve("/*").with(GuiceContainer.class);
            }
        });
    }
}
