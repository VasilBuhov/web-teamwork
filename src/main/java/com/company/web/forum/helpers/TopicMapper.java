package com.company.web.forum.helpers;


import com.company.web.forum.models.Topic;
import com.company.web.forum.models.TopicDto;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {
    private final TopicService topicService;
@Autowired
    public TopicMapper(TopicService topicService) {
        this.topicService = topicService;
    }
    public Topic fromDto(int id, TopicDto dto) {
        Topic topic = fromDto(dto);
        topic.setId(id);
        Topic repositoryTopic = topicService.get(id);
        topic.setViews(repositoryTopic.getViews());
        topic.setLikes(repositoryTopic.getLikes());
        topic.setDislikes(repositoryTopic.getDislikes());
        topic.setPosts(repositoryTopic.getPosts());
        return topic;
    }
    public Topic fromDto(TopicDto dto) {
    Topic topic = new Topic();
    topic.setTitle(dto.getTitle());
    topic.setContent(dto.getContent());
    return topic;
    }

}
