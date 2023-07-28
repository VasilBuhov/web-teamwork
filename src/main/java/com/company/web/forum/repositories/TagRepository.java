package com.company.web.forum.repositories;

import com.company.web.forum.models.FilterTagOptions;
import com.company.web.forum.models.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> get(FilterTagOptions filterTopicOptions);

    Tag get(int id);

    Tag get(String name);

    List<Tag> getByName(String name);

    List<Tag> getAllTags(Integer page, Integer size);

    List<Tag> getAllTags();

    void create(Tag tag);

    void update(Tag tag);

    void delete(int id);

}