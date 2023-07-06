package com.company.web.forum.services;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;

import java.util.List;

public interface TagService {

    List<Tag> get(String name, User belongs_to);

    Tag get(int id);


    void create(Tag tag, User user);

    void update(Tag tag, User user);

    void delete(int id, User user);

}