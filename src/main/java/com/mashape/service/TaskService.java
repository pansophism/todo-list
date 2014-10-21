package com.mashape.service;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mashape.domain.Task;
import com.mashape.exception.CannotInsertException;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by yxzhao on 10/19/14.
 */

@Path("/tasks")
public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);
    private final TaskDao taskDao;

    @Inject
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response listTasks() {
        LOG.info("listing all tasks.");
        Iterable<Task> tasks = taskDao.getAll();
        GenericEntity<List<Task>> entity = new GenericEntity<List<Task>>(Lists.newArrayList(tasks)) {
        };

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response findTask(@PathParam("id") String id) throws TaskNotFoundException {
        LOG.info("Trying to find task : " + id);
        Task aTask = taskDao.get(id);

        GenericEntity<Task> entity = new GenericEntity<Task>(aTask) {
        };

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response updateTask(Task task) throws Exception {
        LOG.info("Trying to update task : " + task);

        boolean result = taskDao.update(task);
        Response.Status status = result ? Response.Status.ACCEPTED : Response.Status.BAD_REQUEST;
        return Response.status(status).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response insertTask(Task task) throws Exception {
        LOG.info("Trying to insert task : " + task);

        return Response.status(Response.Status.CREATED).entity(taskDao.insert(task)).build();
    }

    @DELETE
    @Path("/{id}")
    public final Response deleteTask(@PathParam("id") String taskID) throws Exception {
        LOG.info("Trying to delete task : " + taskID);

        taskDao.delete(taskID);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
