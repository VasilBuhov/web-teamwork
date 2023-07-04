package com.company.web.forum.repositories;

import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicRepository {
    List<Topic> getAllTopics();
    Topic getTopicById(int id);
    void createTopic(Topic topic, User user);
    void deleteTopic(Topic topic, User user);
}
