package com.mashape.dao;

import com.mashape.domain.Task;
import com.mashape.interfaces.TaskDao;

/**
 * Created by yxzhao on 10/17/14.
 */
public class TaskDaoMemImpl implements TaskDao {

    @Override
    public Task get(final long id) {
        return null;
    }

    @Override
    public boolean update(final Task task) {
        return false;
    }

    @Override
    public boolean insert(final Task task) {
        return false;
    }

    @Override
    public boolean delete(final long id) {
        return false;
    }

    @Override
    public boolean delete(final Task task) {
        return false;
    }
}
