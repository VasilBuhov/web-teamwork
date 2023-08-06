package com.company.web.forum.helpers;


import com.company.web.forum.models.PostDto;
import com.company.web.forum.models.Tag;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.TopicDto;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopicMapper {
    private final TopicService topicService;
    private final PostMapper postMapper;

    @Autowired
    public TopicMapper(TopicService topicService, PostMapper postMapper) {
        this.topicService = topicService;
        this.postMapper = postMapper;
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
    public Topic updateTopicDto(TopicDto dto, int id) {
        Topic topic = topicService.get(id);
        topic.setTitle(dto.getTitle());
        topic.setContent(dto.getContent());
        return topic;
    }

    public TopicDto toDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setCreator(topic.getCreator().getUsername());
        topicDto.setTitle(topic.getTitle());
        topicDto.setContent(topic.getContent());
        Set<Tag> tags = topic.getTags();
        Set<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        topicDto.setTags(tagNames);
        topicDto.setCreationDate(topic.getCreationDate());
        topicDto.setLikes(topic.getLikes());
        List<PostDto> posts = postMapper.postDtoList(topic.getPosts().stream().collect(Collectors.toList()));
        topicDto.setPosts(posts);
        topicDto.setViews(topic.getViews());
        return topicDto;
    }

    public List<TopicDto> topicDtoList(List<Topic> topics) {
        return topics.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
