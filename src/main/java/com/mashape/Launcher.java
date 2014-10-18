package com.mashape;

import com.google.inject.Singleton;
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

  public static void main(String[] args) throws Exception
  {
//    Server server = new Server(8080);
//
//    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//    context.setContextPath("/");
//    server.setHandler(context);
//
//    ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/webapi/*");
//    jerseyServlet.setInitOrder(1);
//    jerseyServlet.setInitParameter("jersey.config.server.provider.packages","com.mashape.controllers");
//
//    ServletHolder staticServlet = context.addServlet(DefaultServlet.class,"/*");
//    staticServlet.setInitParameter("resourceBase","src/main/webapp");
//    staticServlet.setInitParameter("pathInfoOnly","true");
//
//    try
//    {
//      server.start();
//      server.join();
//    }
//    catch (Throwable t)
//    {
//      t.printStackTrace(System.err);
//    }
  }
}
