package com.mashape.exception;

import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/20/14.
 */
public class TaskNotFoundException extends TodoListGenericException {
    public TaskNotFoundException(final String message) {
        super(message);
    }

    @Override
    final int getStatusCode() {
        return Response.Status.NOT_FOUND.getStatusCode();
    }
}
