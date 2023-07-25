package com.company.web.forum.helpers;


import com.company.web.forum.models.Topic;
import com.company.web.forum.models.TopicDto;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
public class TopicMapper {
    private final TopicService topicService;

    @Autowired
    public TopicMapper(TopicService topicService) {
        this.topicService = topicService;
    }

    public Topic fromDto(int id, TopicDto dto) {
        Topic topic = CreateTopicDto(dto);
        topic.setId(id);
        Topic repositoryTopic = topicService.get(id);
        topic.setViews(repositoryTopic.getViews());
        topic.setLikes(repositoryTopic.getLikes());
        topic.setPosts(repositoryTopic.getPosts());
        return topic;
    }

    public Topic CreateTopicDto(TopicDto dto) {
        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setContent(dto.getContent());
        topic.setCreationDate(LocalDateTime.now());
        topic.setPosts(new HashSet<>());
        topic.setLikedBy(new HashSet<>());
        topic.setLikes(0);
        topic.setViews(0);
        return topic;
    }

    public TopicDto toDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setCreator(topic.getCreator());
        topicDto.setTitle(topic.getTitle());
        topicDto.setContent(topic.getContent());
        topicDto.setTag(topic.getTag());
        topicDto.setCreationDate(topic.getCreationDate());
        topicDto.setLikes(topic.getLikes());
        topicDto.setPosts(topic.getPosts());
        return topicDto;
    }

}
