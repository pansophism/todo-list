package com.mashape.exception;

import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/20/14.
 */
public class CannotInsertException extends TodoListGenericException {
    public CannotInsertException(String message) {
        super(message);
    }

    @Override
    int getStatusCode() {
        return Response.Status.BAD_REQUEST.getStatusCode();
    }
}
