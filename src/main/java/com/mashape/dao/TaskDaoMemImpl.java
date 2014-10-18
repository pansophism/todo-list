package com.mashape.dao;

import com.mashape.domain.Task;
import com.mashape.interfaces.TaskDao;

/**
 * Created by yxzhao on 10/17/14.
 */
public class TaskDaoMemImpl implements TaskDao
{

  @Override
  public Task get(long id)
  {
    return null;
  }

  @Override
  public boolean update(Task task)
  {
    return false;
  }

  @Override
  public boolean insert(Task task)
  {
    return false;
  }

  @Override
  public boolean delete(long id)
  {
    return false;
  }

  @Override
  public boolean delete(Task task)
  {
    return false;
  }
}
