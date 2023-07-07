package com.company.web.forum.services;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;

import java.util.List;

public interface TagService {

    List<Tag> get(String name, User belongs_to);

    Tag getTagById(int id);

    List<Tag> getAllTags();

    void create(Tag tag, User user);

    void update(Tag tag, User user);

    void delete(int id, User user);

}