package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.helpers.ParseContent;
import com.company.web.forum.helpers.ParseSmileys;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    private static final String MODIFY_TOPIC_ERROR_MESSAGE = "Only admin or topic creator can modify a topic.";
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
        Topic topic = repository.get(id);
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Topic", "ID", String.valueOf(id));
        }
        topic.setViews(topic.getViews() + 1);
        repository.updateViews(topic);
        topic.setContent(ParseSmileys.replaceSmileys(topic.getContent()));
        topic.setContent(ParseContent.formatText(topic.getContent()));
        return topic;
    }
    @Override
    public List<Topic> getTopicsByUser(User user) {

        List<Topic> resultList = repository.getTopicsByUser(user);
        for (Topic topic : resultList) {
            topic.setContent(ParseSmileys.replaceSmileys(topic.getContent()));
            topic.setContent(ParseContent.formatText(topic.getContent()));
        }
        return resultList;
    }

    @Override
    public List<Topic> get10(String order, String sortBy) {
        FilterTopicOptions sort = new FilterTopicOptions();
        Optional<String> orderCriteria = Optional.of(order);
        Optional<String> sortByCriteria = Optional.of(sortBy);
        sort.setSortOrder(orderCriteria);
        sort.setSortBy(sortByCriteria);
        List<Topic> resultList = repository.get(sort);
        for (Topic topic : resultList) {
            topic.setContent(ParseSmileys.replaceSmileys(topic.getContent()));
            topic.setContent(ParseContent.formatText(topic.getContent()));
        }
        return repository.get10(resultList);
    }
    @Override
    public void create(Topic topic, User user) {
        topic.setCreator(user);
        topic.setCreationDate(LocalDateTime.now());
        topic.setTags(new HashSet<>());
        topic.setPosts(new HashSet<>());
        topic.setLikedBy(new HashSet<>());
        topic.setLikes(0);
        topic.setViews(0);
        topic.setStatusDeleted(0);
        repository.create(topic);

    }

    @Override
    public void delete(int id, User user) {
        Topic topicToBeDeleted = repository.get(id);
        if (topicToBeDeleted.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        checkModifyPermissions(id, user);
        repository.delete(id);
    }

    @Override
    public void update(Topic topic, User user) {
        checkModifyPermissions(topic.getId(), user);
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(topic.getId()));
        }
        repository.update(topic);
    }
    public void updateLike(Topic topic, User user) {
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(topic.getId()));
        }
        if (topic.getLikedBy().contains(user)) {
            topic.setLikes(topic.getLikes() - 1);
            topic.getLikedBy().remove(user);
            repository.updateLike(topic);
        } else {
            topic.setLikes(topic.getLikes() + 1);
            topic.getLikedBy().add(user);
            repository.updateLike(topic);
        }
    }
    private void checkModifyPermissions(int topicId, User user) {
        Topic topic = repository.get(topicId);
        if (!(user.getIsAdmin() == 1 || topic.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_TOPIC_ERROR_MESSAGE);
        }
    }
}
