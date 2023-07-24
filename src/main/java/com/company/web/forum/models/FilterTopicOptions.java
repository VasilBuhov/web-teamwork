package com.company.web.forum.models;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Optional;

public class FilterTopicOptions {
    private Optional<String> creatorUsername;
    private Optional<String> tagTitle;
    private Optional<String> title;
    private Optional<LocalDateTime> minCreationDate;
    private Optional<LocalDateTime> maxCreationDate;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FilterTopicOptions() {
        this(null, null, null, null, null, null, null);
    }
    public FilterTopicOptions(String creatorUsername, String tagTitle, String title, LocalDateTime minCreationDate, LocalDateTime maxCreationDate, String sortBy, String sortOrder) {
        this.creatorUsername = Optional.ofNullable(creatorUsername);
        this.tagTitle = Optional.ofNullable(tagTitle);
        this.title = Optional.ofNullable(title);
        this.minCreationDate = Optional.ofNullable(minCreationDate);
        this.maxCreationDate = Optional.ofNullable(maxCreationDate);
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

    public Optional<LocalDateTime> getMinCreationDate() {
        return minCreationDate;
    }

    public Optional<LocalDateTime> getMaxCreationDate() {
        return maxCreationDate;
    }
}
