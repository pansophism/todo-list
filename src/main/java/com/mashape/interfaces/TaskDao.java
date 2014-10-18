package com.mashape.interfaces;

import com.mashape.domain.Task;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface TaskDao
{

  Task get(long id);

  boolean update(Task task);

  boolean insert(Task task);

  boolean delete(long id);

  boolean delete(Task task);

}
