package com.mashape.common;

import com.google.inject.Inject;
import com.mashape.domain.Task;
import com.mashape.interfaces.Indexer;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yxzhao on 10/20/14.
 */
public class SearchlyIndexerImpl extends SearchlyBase implements Indexer {

    private static final Logger LOG
            = LoggerFactory.getLogger(SearchlyIndexerImpl.class);

    private final AppConfig CONFIG = AppConfig.getInstance();

    private final JestClient client;
    private int threadCount = CONFIG.getInt("searchly.indexer.thread.count");

    private ExecutorService pool = Executors.newFixedThreadPool(threadCount);

    @Inject
    public SearchlyIndexerImpl(final JestClient client) {
        this.client = client;
    }

    @Override
    public final void update(final Task task) throws Exception {
        LOG.info("Updating task : " + task);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                Update update
                        = new Update.Builder(task).index(INDEX_NAME).type(INDEX_TYPE).build();

                try {
                    client.execute(update);
                } catch (Exception e) {
                    LOG.error("Exception : ", e);
                }
            }
        });
    }

    @Override
    public final void delete(final String taskID) throws Exception {
        LOG.info("Deleting task by ID : " + taskID);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                Delete delete
                        = new Delete.Builder(taskID).index(INDEX_NAME).type(INDEX_TYPE).build();

                try {
                    client.execute(delete);
                } catch (Exception e) {
                    LOG.error("Exception : ", e);
                }
            }
        });
    }

    @Override
    public final void index(final Task task) throws Exception {
        LOG.info("Indexing task : " + task);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                Index index
                        = new Index.Builder(task).index(INDEX_NAME).type(INDEX_TYPE).build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    LOG.error("Exception : ", e);
                }
            }
        });
    }

}
