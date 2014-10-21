package com.mashape.exception;

/**
 * Created by yxzhao on 10/20/14.
 */
public abstract class TodoListGenericException extends Exception {
    public TodoListGenericException(final String message) {
        super(message);
    }

    abstract int getStatusCode();

}
