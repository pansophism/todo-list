package com.mashape.interfaces;

import com.mashape.domain.Task;

import java.io.IOException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao {

    Task get(final long id) throws IOException;

    boolean insert(final Task task) throws IOException;

    boolean update(final Task task);

    boolean delete(final long id);

    boolean delete(final Task task);

}
