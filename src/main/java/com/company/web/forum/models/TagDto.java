package com.company.web.forum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto {

    @NotNull(message = "Name can't be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 symbols")
    private String name;

    @NotNull(message = "User id must be positive")
    private User belongs_to;

    public TagDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getBelongs_to() {
        return belongs_to;
    }

    public void setBelongs_to(User belongs_to) {
        this.belongs_to = belongs_to;
    }

}