package com.mashape.service;

import com.mashape.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/17/14.
 */

@Path("/hello")
@Produces("application/json")
public class HelloWorldService {

  private final Logger LOG = LoggerFactory.getLogger(getClass());


  @GET
  @Path("/task")
  public Response getTask(@PathParam("param") String msg) {

    LOG.info("User input : " + msg);

    String output = "Jersey say : " + msg;
    Task task = new Task();
    task.setTitle("title");
    task.setContent("content");
    task.setDone(true);

    return Response.status(200).entity(task).build();

  }
}
