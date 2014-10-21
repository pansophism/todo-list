package com.mashape.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mashape.common.SearchlyIndexerImpl;
import com.mashape.common.SearchlySearcherImpl;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoMongoImpl;
import com.mashape.dao.TaskDaoWithNotificationImpl;
import com.mashape.interfaces.Indexer;
import com.mashape.interfaces.NotificationService;
import com.mashape.interfaces.Searcher;
import com.mashape.interfaces.TaskDao;
import com.mashape.service.TwilioNotificationImpl;
import com.mongodb.MongoClient;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.twilio.sdk.TwilioRestClient;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ToDoListAppModule());
    }
}
