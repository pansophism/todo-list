package com.mashape.service;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mashape.domain.Task;
import com.mashape.exception.NotUpdatableException;
import com.mashape.exception.TaskNotFoundException;
import com.mashape.interfaces.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by yxzhao on 10/19/14.
 */

@Path("/tasks")
public class TaskService {

    private static final Logger LOG
            = LoggerFactory.getLogger(TaskService.class);
    private final TaskDao taskDao;

    @Inject
    public TaskService(final TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response listTasks() {
        LOG.info("listing all tasks.");
        Iterable<Task> tasks = taskDao.getAll();
        GenericEntity<List<Task>> entity
                = new GenericEntity<List<Task>>(Lists.newArrayList(tasks)) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response findTask(@PathParam("id") final String id)
            throws TaskNotFoundException {
        LOG.info("Trying to find task : " + id);
        Task aTask = taskDao.get(id);

        GenericEntity<Task> entity
                = new GenericEntity<Task>(aTask) {};

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @PUT
    @Path("/done/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response markTaskDone(@PathParam("id") final String id) throws Exception {
        LOG.info("Trying to mark task done : " + id);

        Task oldTask = taskDao.get(id);
        if(oldTask.isDone()) {
            throw new NotUpdatableException("Task was already done : " + oldTask);
        }

        oldTask.setDone(true);
        boolean result = taskDao.update(oldTask);
        Response.Status status = result
                ? Response.Status.ACCEPTED : Response.Status.BAD_REQUEST;

        return Response.status(status).build();
    }

    @PUT
    @Path("/notdone/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response markTaskNotDone(@PathParam("id") final String id) throws Exception {
        LOG.info("Trying to mark task as not done : " + id);

        Task oldTask = taskDao.get(id);
        if(!oldTask.isDone()) {
            throw new NotUpdatableException("Task was indeed not done : " + oldTask);
        }

        oldTask.setDone(false);
        boolean result = taskDao.update(oldTask);
        Response.Status status = result
                ? Response.Status.ACCEPTED : Response.Status.BAD_REQUEST;

        return Response.status(status).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response updateTask(final Task task) throws Exception {
        LOG.info("Trying to update task : " + task);

        boolean result = taskDao.update(task);
        Response.Status status = result
                ? Response.Status.ACCEPTED : Response.Status.BAD_REQUEST;

        return Response.status(status).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response insertTask(final Task task) throws Exception {
        LOG.info("Trying to insert task : " + task);

        return Response.status(Response.Status.CREATED)
                .entity(taskDao.insert(task)).build();
    }

    @DELETE
    @Path("/{id}")
    public final Response deleteTask(@PathParam("id") final String taskID)
            throws Exception {

        LOG.info("Trying to delete task : " + taskID);

        taskDao.delete(taskID);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
