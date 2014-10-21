package com.mashape.interfaces;

import com.mashape.domain.Task;

/**
 * Created by yxzhao on 10/20/14.
 */
public interface Indexer {

    void update(Task task) throws Exception;

    void delete(String taskID) throws Exception;

    void index(Task task) throws Exception;
}
