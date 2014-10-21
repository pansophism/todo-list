package com.mashape.interfaces;

import com.mashape.domain.Task;
import com.mashape.exception.TaskNotFoundException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Iterable<Task> getAll();

    Task get(String id) throws TaskNotFoundException;

    Task insert(Task task) throws Exception;

    boolean update(Task task) throws Exception;

    void delete(String id) throws Exception;

}
