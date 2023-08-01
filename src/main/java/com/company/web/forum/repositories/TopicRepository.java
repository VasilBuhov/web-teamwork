package com.company.web.forum.repositories;

import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;


import java.util.List;

public interface TopicRepository {
    List<Topic> get(FilterTopicOptions filterTopicOptions);

    List<Topic> get10recent(List<Topic> resultList);

    Topic get(int id);
    Topic get(String title);
    void create(Topic topic);
    void updateLike(Topic topic);
    void updateViews(Topic topic);
    void delete(int id);
    void update(Topic topic);
}
