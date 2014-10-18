package com.mashape.domain;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by yxzhao on 10/18/14.
 */
public class Message {
    private String from;
    private String to;
    private String body;

    public Message(final String from, final String to, final String body) {

        checkNotNull(to, "Has to specify destination of a message", to);
        checkNotNull(from, "Has to specify source of a message", from);
        checkNotNull(body, "Message boday cannot be null", body);

        this.from = from;
        this.to = to;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }
}
