package com.company.web.forum.services;

import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface TopicService {
    List<Topic> get(FilterTopicOptions filterTopicOptions);

    List<Topic> getTopicsByUser(User user);

    Topic replaceSmileys(Topic topic);

    List<Topic> get10(String order, String sortBy);


    Topic get(int id);

    void create(Topic topic, User user);

    void updateLike(Topic topic, User user);

    void delete(int id, User user);

    void update(Topic topic, User user);
}
