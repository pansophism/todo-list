package com.mashape.config;

import com.google.inject.Provides;
import com.mashape.common.SearchlyIndexerImpl;
import com.mashape.common.SearchlySearcherImpl;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.dao.TaskDaoWithIndexingImpl;
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
 * Created by yxzhao on 10/20/14.
 */
public class ToDoListAppModule extends JerseyServletModule {

    private static AppConfig CONFIG = AppConfig.getInstance();
    final ResourceConfig resourceConfig
            = new PackagesResourceConfig("com.mashape.service", "com.mashape.exception");

    @Provides
    public JestClient getJestClient() {
        String connectionUrl
                = CONFIG.getString("searchly.url");
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl)
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }

    @Provides
    TwilioRestClient provideTwiloRestClient() {
        return new TwilioRestClient(
                CONFIG.getString("twilio.account.sid"),
                CONFIG.getString("twilio.auth.token"));
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

        bind(TaskDao.class).to(TaskDaoWithIndexingImpl.class).in(Singleton.class);
        bind(NotificationService.class).to(TwilioNotificationImpl.class).in(Singleton.class);
        bind(Searcher.class).to(SearchlySearcherImpl.class).in(Singleton.class);
        bind(Indexer.class).to(SearchlyIndexerImpl.class).in(Singleton.class);

        for (Class<?> resource : resourceConfig.getClasses()) {
            bind(resource);
        }

        serve("/*").with(GuiceContainer.class);
    }
}
