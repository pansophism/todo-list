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
    private final static int CLIENT_ERROR = 400;
    private final static int SERVER_ERROR = 500;

    @Override
    public final Response toResponse(final Throwable exception) {

        int errorCode = SERVER_ERROR;

        if(exception instanceof TodoListGenericException) {
            errorCode = CLIENT_ERROR;
        }

        return Response.status(errorCode)
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
