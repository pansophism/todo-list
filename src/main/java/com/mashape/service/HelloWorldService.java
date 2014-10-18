package com.mashape.service;

import com.mashape.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/17/14.
 */

@Path("/hello")
public class HelloWorldService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @GET
    @Path("/task")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getTask(@PathParam("param") final String msg) {

        logger.info("User input : " + msg);

        String output = "Jersey say : " + msg;
        Task task = new Task();
        task.setTitle("title");
        task.setContent("content");
        task.setDone(true);

        return Response.status(Response.Status.OK).entity(task).build();

    }
}
