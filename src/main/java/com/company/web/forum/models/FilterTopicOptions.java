package com.company.web.forum.models;

import java.time.LocalDateTime;
import java.util.Optional;

public class FilterTopicOptions {
    private Optional<String> creatorUsername;
    private Optional<String> tagTitle;
    private Optional<String> title;
    private Optional<Integer> minLikes;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FilterTopicOptions() {
        this(null, null, null, null, null, null);
    }

    public FilterTopicOptions(String creatorUsername, String tagTitle, String title, Integer minLikes, String sortBy, String sortOrder) {
        this.creatorUsername = Optional.ofNullable(creatorUsername);
        this.tagTitle = Optional.ofNullable(tagTitle);
        this.title = Optional.ofNullable(title);
        this.minLikes = Optional.ofNullable(minLikes);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getCreatorUsername() {
        return creatorUsername;
    }

    public Optional<String> getTagTitle() {
        return tagTitle;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public Optional<Integer> getMinCreationDate() {
        return minLikes;
    }


    public void setCreatorUsername(Optional<String> creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public void setTagTitle(Optional<String> tagTitle) {
        this.tagTitle = tagTitle;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public void setMinCreationDate(Optional<Integer> minCreationDate) {
        this.minLikes = minLikes;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }


}
