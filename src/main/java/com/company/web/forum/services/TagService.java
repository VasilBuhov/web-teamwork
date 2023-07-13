package com.company.web.forum.services;

import com.company.web.forum.models.*;

import java.util.List;

public interface TagService {

    Tag getTagById(int id);

    List<Tag> get(FilterTagOptions filterTopicOptions);

    List<Tag> getAllTags();

    void create(String tagName, User belongsToUser, Topic occurrenceIn);

    void update(Tag tag, User user);

    void delete(int id, User user);

}