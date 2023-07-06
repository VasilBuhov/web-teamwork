package com.company.web.forum.repositories;

import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicRepository {
    List<Topic> get();
    Topic get(int id);
    Topic get(String title);
    Topic get(User creator);
    void create(Topic topic);
    void delete(int id);
    void update(Topic topic);
}
