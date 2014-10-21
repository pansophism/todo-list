package com.mashape.common;

import com.google.inject.Inject;
import com.mashape.domain.Task;
import com.mashape.interfaces.Indexer;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Update;

/**
 * Created by yxzhao on 10/20/14.
 */
public class SearchlyIndexerImpl extends SearchlyBase implements Indexer {
    private final JestClient client;

    @Inject
    public SearchlyIndexerImpl(JestClient client) {
        this.client = client;
    }

    @Override
    public void update(Task task) throws Exception {
        Update update = new Update.Builder(task).index(indexName).type(type).build();
        client.execute(update);
    }

    @Override
    public void delete(String taskID) throws Exception {
        Delete delete = new Delete.Builder(taskID).index(indexName).type(type).build();
        client.execute(delete);
    }

    @Override
    public void index(Task task) throws Exception {
        Index index = new Index.Builder(task).index(indexName).type(type).build();
        client.execute(index);
    }

}
