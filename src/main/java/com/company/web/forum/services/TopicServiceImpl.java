package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private static final String MODIFY_POST_ERROR_MESSAGE = "Only admin or topic creator can modify a topic.";
    private final TopicRepository repository;

    @Autowired
    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Topic> get(FilterTopicOptions filterTopicOptions) {
        return repository.get(filterTopicOptions);
    }

    @Override
    public Topic get(int id) {
        return repository.get(id);
    }

    @Override
    public void create(Topic topic, User user) {
        topic.setCreator(user);
        repository.create(topic);

    }

    @Override
    public void delete(int id, User user) {
        checkModifyPermissions(id, user);
        repository.delete(id);
    }

    @Override
    public void update(Topic topic, User user) {
        checkModifyPermissions(topic.getId(), user);
        repository.update(topic);
    }

    private void checkModifyPermissions(int topicId, User user) {
        Topic topic = repository.get(topicId);
        if (!(user.isAdmin() || topic.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
