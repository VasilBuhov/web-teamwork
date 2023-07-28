package com.company.web.forum.services;

import com.company.web.forum.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag getTagById(int id);

    List<Tag> get(FilterTagOptions filterTopicOptions);

    List<Tag> getAllTags(Integer  page, Integer  size);

    List<Tag> getAllTags();

    void create(String tagName, User belongsToUser);
    void createTagForTopic(String tagName, User belongsToUser, Topic topic);
    void removeTagFromTopic(String tagName, User user, Topic topic);

    void update(Tag tag, User user);

    void delete(int id, User user);

    Page<Tag> findPaginated(Pageable pageable);
}