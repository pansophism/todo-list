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
public class UnCheckedExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        return Response.status((exception instanceof TodoListGenericException) ? 400 : 500)
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
