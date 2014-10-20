package com.mashape.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by yxzhao on 10/18/14.
 */
public class Message {
    private String to;
    private String body;

    public Message(final String to, final String body) {

        checkNotNull(to, "Has to specify destination of a message", to);
        checkNotNull(body, "Message boday cannot be null", body);

        this.to = to;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }
}
