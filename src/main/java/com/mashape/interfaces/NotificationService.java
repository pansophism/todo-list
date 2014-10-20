package com.mashape.interfaces;

import com.mashape.domain.Message;
import com.twilio.sdk.TwilioRestException;

/**
 * Created by yxzhao on 10/17/14.
 */
public interface NotificationService {
    void notify(Message msg) throws TwilioRestException;
}
