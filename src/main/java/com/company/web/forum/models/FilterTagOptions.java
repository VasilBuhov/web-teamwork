package com.company.web.forum.models;

import java.util.Optional;

public class FilterTagOptions {
    private Optional<User> creator;
    private Optional<Tag> tag;

    public FilterTagOptions() {
        this(null, null);
    }

    public FilterTagOptions(User creator, Tag tag) {
        this.creator = Optional.ofNullable(creator);
        this.tag = Optional.ofNullable(tag);
    }

    public Optional<User> getCreator() {
        return creator;
    }

    public Optional<Tag> getTag() {
        return tag;
    }
}