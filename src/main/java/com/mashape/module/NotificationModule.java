package com.mashape.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mashape.common.AppConfig;
import com.mashape.interfaces.NotificationService;
import com.mashape.service.TwilioNotificationImpl;
import com.twilio.sdk.TwilioRestClient;

import javax.inject.Singleton;

/**
 * Created by yxzhao on 10/17/14.
 */
public class NotificationModule extends AbstractModule {

    private static AppConfig CONFIG = AppConfig.getInstance();

    @Provides
    TwilioRestClient provideTwiloRestClient() {
        return new TwilioRestClient(
                CONFIG.getString("twilio.account.sid"),
                CONFIG.getString("twilio.auth.token"));
    }

    @Override
    protected final void configure() {
        bind(NotificationService.class).to(TwilioNotificationImpl.class).in(Singleton.class);
    }
}
