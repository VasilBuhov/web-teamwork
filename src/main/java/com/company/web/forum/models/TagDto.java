package com.company.web.forum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

public class TagDto {

    @NotNull(message = "Name can't be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 symbols")
    private String name;

    //@NotNull(message = "User id must be positive")
    private User belongs_to;

    private OffsetDateTime creationDate;

    private String belongsToStringed;

    public TagDto() {
    }

    public String getBelongsToStringed() {
        return belongsToStringed;
    }

    public void setBelongsToStringed(String belongsToStringed) {
        this.belongsToStringed = belongsToStringed;
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

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

}