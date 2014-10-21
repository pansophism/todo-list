package com.mashape.interfaces;

import com.mashape.common.Constants;
import com.mashape.domain.Task;

/**
 * Created by yxzhao on 10/20/14.
 */
public interface Searcher {

    Iterable<Task> search(String query) throws Exception;

    Iterable<Task> searchByField(Constants.Fileds filed, String value) throws Exception;
}
