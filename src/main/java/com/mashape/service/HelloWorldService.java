package com.mashape.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


/**
 * Created by yxzhao on 10/17/14.
 */

@Path("/hello")
public class HelloWorldService {

  @GET
  @Path("/{param}")
  public Response getMsg(@PathParam("param") String msg) {

    String output = "Jersey say : " + msg;

    return Response.status(200).entity(output).build();

  }

}
