package com.mashape.interfaces;

import com.mashape.domain.NotUpdatableException;
import com.mashape.domain.Task;

import java.io.IOException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Iterable<Task> getAll() throws IOException;

    Task get(String id) throws IOException;

    Task insert(Task task) throws IOException;

    boolean update(Task task) throws NotUpdatableException;

    void delete(String id);

    void delete(Task task);

}
