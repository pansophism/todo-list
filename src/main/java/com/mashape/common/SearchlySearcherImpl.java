package com.mashape.common;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mashape.domain.Task;
import com.mashape.interfaces.Searcher;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by yxzhao on 10/20/14.
 */
public class SearchlySearcherImpl extends SearchlyBase implements Searcher {
    private final JestClient client;

    @Inject
    public SearchlySearcherImpl(JestClient client) {
        this.client = client;
    }

    @Override
    public Iterable<Task> search(String query) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryString(query));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(INDEX_NAME)
                .addType(INDEX_TYPE)
                .build();

        return performSearch(search);
    }

    @Override
    public Iterable<Task> searchByField(Constants.Fileds filed, String value) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(filed.toString(), value));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(INDEX_NAME)
                .addType(INDEX_TYPE)
                .build();

        return performSearch(search);
    }

    private Iterable<Task> performSearch(Search search) throws Exception {
        JestResult result = client.execute(search);

        List<SearchResult.Hit<Task, Void>> hits = ((SearchResult) result).getHits(Task.class);

        return Lists.transform(hits, new Function<SearchResult.Hit<Task, Void>, Task>() {
            @Nullable
            @Override
            public Task apply(@Nullable SearchResult.Hit<Task, Void> aHit) {
                return aHit.source;
            }
        });
    }

}
