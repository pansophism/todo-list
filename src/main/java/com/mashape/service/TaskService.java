package com.mashape.service;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mashape.domain.NotUpdatableException;
import com.mashape.domain.Task;
import com.mashape.interfaces.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created by yxzhao on 10/19/14.
 */

@Path("/tasks")
public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldService.class);
    private final TaskDao taskDao;

    @Inject
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response listTasks() throws IOException {
        Iterable<Task> tasks = taskDao.getAll();
        GenericEntity<List<Task>> entity = new GenericEntity<List<Task>>(Lists.newArrayList(tasks)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response findTask(@QueryParam("id") String id) throws IOException {
        Task aTask = taskDao.get(id);
        GenericEntity<Task> entity = new GenericEntity<Task>(aTask) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response updateTask(Task task) throws IOException, NotUpdatableException {
        boolean result = taskDao.update(task);
        Response.Status status = result ? Response.Status.OK : Response.Status.BAD_REQUEST;
        return Response.status(status).build();
    }

    @PUT
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response insertTask(Task task) throws IOException {
        taskDao.insert(task);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response insertTask(@PathParam("id") String taskID) throws IOException {
        taskDao.delete(taskID);
        return Response.status(Response.Status.OK).build();
    }

}
