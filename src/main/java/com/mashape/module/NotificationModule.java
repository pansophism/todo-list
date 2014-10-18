package com.mashape.module;

import com.google.inject.AbstractModule;
import com.mashape.interfaces.NotificationService;
import com.mashape.service.NotificationServiceImpl;

/**
 * Created by yxzhao on 10/17/14.
 */
public class NotificationModule extends AbstractModule {

    @Override
    protected final void configure() {
        bind(NotificationService.class).to(NotificationServiceImpl.class);
    }
}
