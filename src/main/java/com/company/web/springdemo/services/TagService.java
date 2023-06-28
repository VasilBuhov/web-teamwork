package com.company.web.springdemo.services;

import com.company.web.springdemo.models.Tag;
import com.company.web.springdemo.models.User;

import java.util.List;

public interface TagService {

    List<Tag> get(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String sortOrder);

    Tag get(int id);

    void create(Tag tag, User user);

    void update(Tag tag, User user);

    void delete(int id, User user);

}