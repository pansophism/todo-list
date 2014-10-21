package com.mashape.exception;

import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/19/14.
 */
public class NotUpdatableException extends TodoListGenericException {
    public NotUpdatableException(final String message) {
        super(message);
    }

    @Override
    final int getStatusCode() {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }
}
