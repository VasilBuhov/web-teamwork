package com.company.web.forum.repositories;

import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryImpl implements TopicRepository{
    private List<Topic> topics;

    public TopicRepositoryImpl() {
        topics = new ArrayList<>();

        Topic topic1 = new Topic(1, "Pishtovi");
        Topic topic2 = new Topic(2, "Pushki");

        topics.add(topic1);
        topics.add(topic2);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topics;
    }

    @Override
    public Topic getTopicById(int id) {
        return null;
    }

    @Override
    public void createTopic(Topic topic, User user) {

    }

    @Override
    public void deleteTopic(Topic topic, User user) {

    }
}
