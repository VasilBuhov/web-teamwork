package com.company.web.forum.services;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;

import java.util.List;

public interface TagService {

    List<Tag> get(String name, int belongs_to, String sortBy);

    Tag get(int id);


    void create(Tag tag, int user);

    void update(Tag tag, User user);

    void delete(int id, User user);

}