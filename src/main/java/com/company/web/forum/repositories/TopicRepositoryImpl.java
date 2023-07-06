package com.company.web.forum.repositories;


import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class TopicRepositoryImpl implements TopicRepository{
    private List<Topic> topics;

    public TopicRepositoryImpl() {
        topics = new ArrayList<>();

        Topic topic1 = new Topic();
        Topic topic2 = new Topic();

        topics.add(topic1);
        topics.add(topic2);
    }

    @Override
    public List<Topic> get() {
        return topics;
    }

    @Override
    public Topic get(int id) {
        return topics.stream()
                .filter(topic -> topic.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Topic", id));
    }
    public Topic get(String title) {
        return topics.stream()
                .filter(topic -> topic.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Topic", "name", title));
    }
    public Topic get(User creator) {
        return topics.stream()
                .filter(topic -> topic.getCreator().equals(creator))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Topic", "creator", creator.getUsername()));
    }

    @Override
    public void create(Topic topic) {
        int nextId = topics.size() + 1;
        topic.setId(nextId);
        topics.add(topic);
    }
    @Override
    public void update(Topic topic, User user) {
        Topic topicToUpdate = get(topic.getId());
        topicToUpdate.setContent(topic.getContent());
        topicToUpdate.setDislikes(topic.getDislikes());
        topicToUpdate.setLikes(topic.getLikes());
        topicToUpdate.setViews(topic.getViews());
        topicToUpdate.setCreator(topic.getCreator());
        topicToUpdate.setTitle(topic.getTitle());
        topicToUpdate.setPosts(topic.getPosts());
    }
    @Override
    public void delete(int id, User user) {
        Topic topicToDelete = get(id);
        topics.remove(topicToDelete);
    }
}
