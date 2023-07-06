package com.company.web.forum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TagDto {

    @NotNull(message = "Name can't be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 symbols")
    private String name;

    @NotNull(message = "Content can't be empty")
    @Size(min = 3, max = 50, message = "Content should be between 3 and 50 symbols")
    private String content;

    @Positive(message = "User id must be positive")
    private int belongs_to;

    public TagDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getBelongs_to() {
        return belongs_to;
    }

    public void setBelongs_to(int belongs_to) {
        this.belongs_to = belongs_to;
    }

}