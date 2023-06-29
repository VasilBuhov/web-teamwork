package com.company.web.springdemo.repositories;

import com.company.web.springdemo.models.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> get(String name, String content, Integer styleId, String sortBy, String sortOrder);

    Tag get(int id);

    Tag get(String name);

    void create(Tag tag);

    void update(Tag tag);

    void delete(int id);

}