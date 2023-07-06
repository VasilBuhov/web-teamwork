package com.company.web.forum.services;

import com.company.web.forum.models.Post;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicService {
    List<Topic> get();
    Topic get(int id);
    Topic get(User creator);
    void create(Topic topic, User user);
    void delete(int id, User user);
    void update(Topic topic, User user);
    }
