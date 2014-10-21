package com.mashape.domain;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.mashape.common.Constants;

import javax.xml.bind.annotation.XmlRootElement;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by yxzhao on 10/17/14.
 */

@XmlRootElement
public class Task {
    private String id;
    private String title;
    private String content;
    private boolean done;

    public Task() {
    }

    public Task(
            final String taskId,
            final String title,
            final String content,
            final boolean done) {

        checkArgument(
                !Strings.isNullOrEmpty(taskId), "Task ID must not be empty", taskId);

        checkNotNull(
                title, "Task title cannot be null.", title);

        this.title = title;
        this.content = content;
        this.done = done;
        this.id = taskId;
    }

    public final String getId() {
        return id;
    }

    public final String getTitle() {
        return title;
    }

    public final void setId(final String taskId) {
        this.id = taskId;
    }

    public final void setTitle(final String title) {
        this.title = title;
    }

    public final String getContent() {
        return content;
    }

    public final void setContent(final String content) {
        this.content = content;
    }

    public final boolean isDone() {
        return done;
    }

    public final void setDone(final boolean done) {
        this.done = done;
    }

    @Override
    public final String toString() {
        return Objects.toStringHelper(this)
                .add(Constants.Fileds.ID.value(), getId())
                .add(Constants.Fileds.TITLE.value(), getTitle())
                .add(Constants.Fileds.CONTENT.value(), getContent())
                .add(Constants.Fileds.DONE.value(), isDone())
                .toString();
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(getId(), getContent(), getTitle());
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;

        return Objects.equal(getId(), otherTask.getId())
                && Objects.equal(getTitle(), otherTask.getTitle())
                && Objects.equal(getContent(), otherTask.getContent())
                && Objects.equal(isDone(), otherTask.isDone());
    }
}
