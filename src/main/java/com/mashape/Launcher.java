package com.mashape;

import com.google.inject.Singleton;
import com.mashape.common.AppConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by yxzhao on 10/17/14.
 */

@Singleton
public final class Launcher {

    private Launcher() {
    }

    private static final AppConfig CONFIG = AppConfig.getInstance();
    private static final int PORT = CONFIG.getInt("server.port", 8080);
    private static final String PACKAGETOSCAN = "com.mashape.service";

    public static void main(final String[] args) throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(1);
        jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages", PACKAGETOSCAN);

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
