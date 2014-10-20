package com.mashape.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoMongoImpl;
import com.mashape.dao.TaskDaoWithNotificationImpl;
import com.mashape.interfaces.NotificationService;
import com.mashape.interfaces.TaskDao;
import com.mashape.service.TwilioNotificationImpl;
import com.mongodb.MongoClient;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.twilio.sdk.TwilioRestClient;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskServletConfig extends GuiceServletContextListener {

    private static AppConfig CONFIG = AppConfig.getInstance();

    @Override
    protected Injector getInjector() {
        final ResourceConfig resourceConfig = new PackagesResourceConfig("com.mashape.service", "com.mashape.exception");
        return Guice.createInjector(new JerseyServletModule() {


            @Provides
            TwilioRestClient provideTwiloRestClient() {

                return new TwilioRestClient(
                        CONFIG.getString("twilio.account.sid", "AC58b1c7c8115823b20bf841ac3702b9d1"),
                        CONFIG.getString("twilio.auth.token", "505e29a45f944dba435d0afa716de766"));
            }

            @Provides
            TaskToMongoObjMapper providesMapper() {
                return new TaskToMongoObjMapper();
            }

            @Provides
            MongoClient provideMongoClient() throws UnknownHostException {
                return new MongoClient(
                        CONFIG.getString("mongodb.server", "localhost"),
                        CONFIG.getInt("mongodb.port", 27017));
            }

            @Override
            protected void configureServlets() {
                super.configureServlets();

                bind(TaskDao.class).to(TaskDaoWithNotificationImpl.class).in(Singleton.class);
                bind(NotificationService.class).to(TwilioNotificationImpl.class).in(Singleton.class);

                for (Class<?> resource : resourceConfig.getClasses()) {
                    bind(resource);
                }

                serve("/*").with(GuiceContainer.class);
            }
        });
    }
}
