package com.mashape.exception;

import com.google.inject.Singleton;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by yxzhao on 10/20/14.
 */

@Provider
@Singleton
public class AppExceptionMapper implements ExceptionMapper<TodoListGenericException> {

    @Override
    public Response toResponse(TodoListGenericException exception) {
        return Response.status(exception.getStatusCode())
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();

    }
}
