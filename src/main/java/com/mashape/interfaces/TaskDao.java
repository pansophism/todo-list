package com.mashape.interfaces;

import com.mashape.domain.Task;
import com.mashape.exception.CannotInsertException;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;

import java.io.IOException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Iterable<Task> getAll();

    Task get(String id) throws TaskNotFoundException;

    Task insert(Task task) throws CannotInsertException;

    boolean update(Task task) throws NotUpdatableException;

    void delete(String id) throws TaskNotFoundException;

    void delete(Task task) throws TaskNotFoundException;

}
