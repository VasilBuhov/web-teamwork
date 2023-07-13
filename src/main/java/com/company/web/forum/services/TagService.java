package com.company.web.forum.services;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TagService {

    List<Tag> get(String name, User belongs_to);

    Tag getTagById(int id);

    List<Tag> getTagByName(String name);

    List<Tag> getAllTags();

    void create(String tagName, User belongsToUser, Topic occurrenceIn);

    void update(Tag tag, User user);

    void delete(int id, User user);

}