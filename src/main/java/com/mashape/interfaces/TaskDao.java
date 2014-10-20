package com.mashape.interfaces;

import com.mashape.domain.Task;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;

import java.io.IOException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Iterable<Task> getAll() throws IOException;

    Task get(String id) throws IOException, TaskNotFoundException;

    Task insert(Task task) throws IOException;

    boolean update(Task task) throws NotUpdatableException;

    void delete(String id) throws IOException, TaskNotFoundException;

    void delete(Task task) throws IOException, TaskNotFoundException;

}
