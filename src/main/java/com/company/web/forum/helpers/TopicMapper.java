package com.company.web.forum.helpers;


import com.company.web.forum.models.Tag;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.TopicDto;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopicMapper {
    private final TopicService topicService;

    @Autowired
    public TopicMapper(TopicService topicService) {
        this.topicService = topicService;
    }

    public Topic updateTopicContentDto(int id, TopicDto dto) {
        Topic oldTopic = topicService.get(id);
        oldTopic.setContent(dto.getContent());
        return oldTopic;
    }
    public Topic updateTopicTitleDto(int id, TopicDto dto) {
        Topic oldTopic = topicService.get(id);
        oldTopic.setTitle(dto.getTitle());
        return oldTopic;
    }

    public Topic createTopicDto(TopicDto dto) {
        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setContent(dto.getContent());
        return topic;
    }

    public TopicDto toDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setCreatorUsername(topic.getCreator().getUsername());
        topicDto.setTitle(topic.getTitle());
        topicDto.setContent(topic.getContent());
        Set<Tag> tags = topic.getTags();
        Set<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        topicDto.setTagNames(tagNames);
        topicDto.setCreationDate(topic.getCreationDate());
        topicDto.setLikes(topic.getLikes());
        topicDto.setPosts(topic.getPosts());
        topicDto.setViews(topic.getViews());
        return topicDto;
    }

}
