package com.company.web.forum.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicDto {
    @NotNull(message = "Title can't be empty.")
    @Size(min = 16, max = 64, message = "Name should be between 16 and 64 symbols.")
    private String title;
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols.")
    private String content;
    @NotNull
    @NotEmpty
    private User creator;

    public TopicDto() {}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
