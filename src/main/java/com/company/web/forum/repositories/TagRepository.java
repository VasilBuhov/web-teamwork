package com.company.web.forum.repositories;

import com.company.web.forum.models.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> get(String name, int belongs_to);

    Tag get(int id);

    Tag get(String name);

    List<Tag> getAllTags();

    void create(Tag tag);

    void update(Tag tag);

    void delete(int id);

}