package com.mashape.dao;

import com.google.inject.Inject;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Task;
import com.mashape.interfaces.Indexer;
import com.mongodb.MongoClient;

/**
 * Created by yxzhao on 10/20/14.
 */
public class TaskDaoWithIndexingImpl extends TaskDaoMongoImpl {

    private final Indexer indexer;

    @Inject
    public TaskDaoWithIndexingImpl(MongoClient mongo, TaskToMongoObjMapper mapper, Indexer indexer) {
        super(mongo, mapper);
        this.indexer = indexer;
    }

    @Override
    public Task insert(Task task) throws Exception {
        Task aTask = super.insert(task);
        indexer.index(task);

        return aTask;
    }

    @Override
    public boolean update(Task task) throws Exception {
        boolean result = super.update(task);
        indexer.update(task);

        return result;
    }

    @Override
    public void delete(String id) throws Exception {
        super.delete(id);
        indexer.delete(id);
    }
}
