package com.mashape.service;

import com.mashape.interfaces.NotificationService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxzhao on 10/17/14.
 */
public class TwilioNotificationImpl implements NotificationService
{

  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC58b1c7c8115823b20bf841ac3702b9d1";
  public static final String AUTH_TOKEN = "{{ auth_token }}";

  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Jenny please?! I love you <3"));
    params.add(new BasicNameValuePair("To", "+15558675309"));
    params.add(new BasicNameValuePair("From", "+14158141829"));
    params.add(new BasicNameValuePair("MediaUrl", "http://www.example.com/hearts.png"));


    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message = messageFactory.create(params);
    System.out.println(message.getSid());
  }

  @Override
  public void notify(com.mashape.domain.Message msg)
  {

  }
}
