package com.mashape.module;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Created by yxzhao on 10/19/14.
 */
public class TaskServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ToDoListAppModule(),
                new TaskModule(),
                new SearchModule(),
                new NotificationModule());
    }
}
