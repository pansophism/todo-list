package com.mashape.interfaces;

import com.mashape.domain.Message;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface NotificationService
{
  public void notify(Message msg);
}