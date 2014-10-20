package com.mashape;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.mashape.config.AppConfig;
import com.mashape.config.TaskServletConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by yxzhao on 10/17/14.
 */

public final class Launcher {

    private static final AppConfig CONFIG = AppConfig.getInstance();
    private static final int PORT = CONFIG.getInt("server.port", 8080);

    private Launcher() {
    }

    public static void main(final String[] args) throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", null);
        context.addEventListener(new TaskServletConfig());
        context.addServlet(DefaultServlet.class, "/");

        server.setHandler(context);

        server.start();
        server.join();
    }
}
