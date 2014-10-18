package com.mashape;

import com.google.inject.Singleton;
import com.mashape.common.AppConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by yxzhao on 10/17/14.
 */

@Singleton
public class Launcher
{

  private static final AppConfig config = AppConfig.getInstance();

  public static void main(String[] args) throws Exception
  {
    Server server = new Server(config.getInt("server.port", 8080));

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
    jerseyServlet.setInitOrder(1);
    jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages","com.mashape.service");

    ServletHolder staticServlet = context.addServlet(DefaultServlet.class,"/static/*");
    staticServlet.setInitParameter("resourceBase","src/main/webapp");
    staticServlet.setInitParameter("pathInfoOnly","true");

    try
    {
      server.start();
      server.join();
    }
    catch (Throwable t)
    {
      t.printStackTrace(System.err);
    }
  }
}
