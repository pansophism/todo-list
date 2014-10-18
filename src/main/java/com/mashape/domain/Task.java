package com.mashape.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by yxzhao on 10/17/14.
 */

@XmlRootElement(name = "Employees")
public class Task
{
  @JsonIgnore
  private long taskId;

  private String title;
  private String content;
  private boolean done;

  public long getTaskId()
  {
    return taskId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public boolean isDone()
  {
    return done;
  }

  public void setDone(boolean done)
  {
    this.done = done;
  }

}
