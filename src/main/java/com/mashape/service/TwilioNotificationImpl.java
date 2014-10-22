package com.mashape.service;

import com.google.inject.Inject;
import com.mashape.common.AppConfig;
import com.mashape.interfaces.NotificationService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxzhao on 10/17/14.
 */
public class TwilioNotificationImpl implements NotificationService {

    private static final Logger LOG
            = LoggerFactory.getLogger(TwilioNotificationImpl.class);

    private static final String FROM_NUMBER
            = AppConfig.getInstance().getString("from.number");

    private final TwilioRestClient client;

    @Inject
    public TwilioNotificationImpl(final TwilioRestClient client) {
        this.client = client;
    }

    @Override
    public final void notify(final com.mashape.domain.Message msg)
            throws TwilioRestException {

        MessageFactory messageFactory = client.getAccount().getMessageFactory();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("From", FROM_NUMBER));
        params.add(new BasicNameValuePair("To", msg.getTo()));
        params.add(new BasicNameValuePair("Body", msg.getBody()));

        Message message = messageFactory.create(params);

        LOG.info("Message sent to : " + message.getTo());
    }
}
