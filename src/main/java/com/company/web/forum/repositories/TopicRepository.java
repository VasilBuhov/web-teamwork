package com.company.web.forum.repositories;

import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicRepository {
    List<Topic> get(FilterTopicOptions filterTopicOptions);
    Topic get(int id);
    Topic get(String title);
    void create(Topic topic);
    void addLike(Topic topic);

    void removeLike(Topic topic);
    void delete(int id);
    void update(Topic topic);
}
