package com.company.web.forum.repositories;

import com.company.web.forum.models.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> get(String name, int belongs_to, String sortBy);

    Tag get(int id);

    Tag get(String name);

    void create(Tag tag);

    void update(Tag tag);

    void delete(int id);

}