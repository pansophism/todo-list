package com.mashape.module;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Created by yxzhao on 10/20/14.
 */
public class ToDoListAppModule extends JerseyServletModule {

    final ResourceConfig resourceConfig
            = new PackagesResourceConfig("com.mashape.service", "com.mashape.exception");

    @Override
    protected void configureServlets() {
        super.configureServlets();

        for (Class<?> resource : resourceConfig.getClasses()) {
            bind(resource);
        }

        serve("/*").with(GuiceContainer.class);
    }
}
