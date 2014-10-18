package com.mashape.module;

import com.google.inject.AbstractModule;
import com.mashape.interfaces.NotificationServiceIF;
import com.mashape.service.NotificationServiceImpl;

/**
 * Created by yxzhao on 10/17/14.
 */
public class NotificationModule extends AbstractModule
{

  @Override
  protected void configure()
  {
    bind(NotificationServiceIF.class).to(NotificationServiceImpl.class);
  }
}
