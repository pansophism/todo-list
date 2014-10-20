package com.mashape.domain;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import javax.xml.bind.annotation.XmlRootElement;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by yxzhao on 10/17/14.
 */

@XmlRootElement
public class Task {
    private String taskId;
    private String title;
    private String content;
    private boolean done;

    public Task() {
    }

    public Task(final String taskId, final String title, final String content, final boolean done) {
        checkArgument(!Strings.isNullOrEmpty(taskId), "Task ID must not be empty", taskId);
        checkNotNull(title, "Task title cannot be null.", title);

        this.title = title;
        this.content = content;
        this.done = done;
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTaskId(final String taskId) {
        this.taskId = taskId;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("TaskID", getTaskId())
                .add("Title", getTitle())
                .add("Content", getContent())
                .add("isDone", isDone())
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTaskId(), getContent(), getTitle());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;

        return Objects.equal(getTaskId(), otherTask.getTaskId())
                && Objects.equal(getTitle(), otherTask.getTitle())
                && Objects.equal(getContent(), otherTask.getContent())
                && Objects.equal(isDone(), otherTask.isDone());
    }
}
