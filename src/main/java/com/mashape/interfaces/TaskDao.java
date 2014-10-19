package com.mashape.interfaces;

import com.mashape.domain.Task;

import java.io.IOException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Iterable<Task> getAll() throws IOException;

    Task get(long id) throws IOException;

    boolean insert(Task task) throws IOException;

    boolean update(Task task);

    boolean delete(long id);

    boolean delete(Task task);

}
