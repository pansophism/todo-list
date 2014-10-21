package com.mashape.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mashape.common.AppConfig;
import com.mashape.common.SearchlyIndexerImpl;
import com.mashape.common.SearchlySearcherImpl;
import com.mashape.interfaces.Indexer;
import com.mashape.interfaces.Searcher;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import javax.inject.Singleton;

/**
 * Created by yxzhao on 10/20/14.
 */
public class SearchModule extends AbstractModule {

    private static final AppConfig CONFIG = AppConfig.getInstance();

    @Provides
    public JestClient getJestClient() {
        String connectionUrl = CONFIG.getString("searchly.url");
        JestClientFactory factory = new JestClientFactory();

        factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl)
                .multiThreaded(true)
                .build());

        return factory.getObject();
    }

    @Override
    protected final void configure() {
        bind(Searcher.class)
                .to(SearchlySearcherImpl.class)
                .in(Singleton.class);

        bind(Indexer.class)
                .to(SearchlyIndexerImpl.class)
                .in(Singleton.class);
    }
}
