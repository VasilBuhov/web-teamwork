package com.company.web.forum.models;


import java.util.Optional;

public class FilterPostOptions {
    private Optional<String> creatorUsername;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FilterPostOptions(String creatorUsername, String sortBy, String sortOrder) {
        this.creatorUsername = Optional.ofNullable(creatorUsername);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getCreatorUsername() {
        return creatorUsername;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
