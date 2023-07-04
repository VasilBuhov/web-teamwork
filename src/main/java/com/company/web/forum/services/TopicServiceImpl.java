package com.company.web.forum.services;

import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TopicServiceImpl implements TopicService{

    @Override
    public List<Topic> getAll() {
        return null;
    }

    @Override
    public Topic getById(int id) {
        return null;
    }

    @Override
    public void create(Topic topic, User user) {

    }

    @Override
    public void delete(Topic topic, User user) {

    }
}
