package com.mashape.exception;

import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/20/14.
 */
public class CannotInsertException extends TodoListGenericException {
    public CannotInsertException(final String message) {
        super(message);
    }

    @Override
    final int getStatusCode() {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }
}
