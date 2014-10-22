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
    public SearchlyIndexerImpl(final JestClient client) {
        this.client = client;
    }

    @Override
    public final void update(final Task task) throws Exception {
        Update update
                = new Update.Builder(task).index(INDEX_NAME).type(INDEX_TYPE).build();

        client.execute(update);
    }

    @Override
    public final void delete(final String taskID) throws Exception {
        Delete delete
                = new Delete.Builder(taskID).index(INDEX_NAME).type(INDEX_TYPE).build();

        client.execute(delete);
    }

    @Override
    public final void index(final Task task) throws Exception {
        Index index
                = new Index.Builder(task).index(INDEX_NAME).type(INDEX_TYPE).build();

        client.execute(index);
    }

}
