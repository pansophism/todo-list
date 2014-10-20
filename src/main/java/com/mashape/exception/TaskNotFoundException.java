package com.mashape.exception;

import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/20/14.
 */
public class TaskNotFoundException extends TodoListGenericException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    @Override
    int getStatusCode() {
        return Response.Status.NOT_FOUND.getStatusCode();
    }
}
