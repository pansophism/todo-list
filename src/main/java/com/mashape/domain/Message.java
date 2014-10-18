package com.mashape.domain;

/**
 * Created by yxzhao on 10/18/14.
 */
public class Message
{
  private String from;
  private String to;
  private String boday;

  public Message(String from, String to, String boday) {
    this.from = from;
    this.to = to;
    this.boday = boday;
  }

  public String getFrom()
  {
    return from;
  }

  public String getTo()
  {
    return to;
  }

  public String getBoday()
  {
    return boday;
  }
}
