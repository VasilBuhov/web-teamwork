package com.company.web.forum.services;

import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicService {
    List<Topic> getAll();
    Topic getById(int id);
    void create(Topic topic, User user);
    void delete(Topic topic, User user);
    }
