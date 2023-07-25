package com.company.web.forum.controllers.RestController;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.TopicMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {
    private final TopicService service;
    private final TopicMapper topicMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TopicRestController(TopicService service, TopicMapper topicMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.topicMapper = topicMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Topic> get(
            @RequestParam(required = false) String creatorUsername,
            @RequestParam(required = false) String tagTitle,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDateTime minCreationDate,
            @RequestParam(required = false) LocalDateTime maxCreationDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        FilterTopicOptions filterTopicOptions = new FilterTopicOptions(creatorUsername, tagTitle, title, minCreationDate, maxCreationDate, sortBy, sortOrder);
        return service.get(filterTopicOptions);
    }

    @GetMapping("/{id}")
    public TopicDto get(@PathVariable int id) {
        try {
            Topic topic = service.get(id);
            return topicMapper.toDto(topic);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Topic create(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody TopicDto topicDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            Topic topic = topicMapper.createTopicDto(topicDto);
            service.create(topic, user);
            return topic;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/content")
    public Topic updateContent(@PathVariable int id, @RequestHeader HttpHeaders httpheaders, @Valid @RequestBody TopicDto topicDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Topic topic = topicMapper.updateTopicContentDto(id, topicDto);
            service.update(topic, user);
            return topic;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("/{id}/title")
    public Topic updateTitle(@PathVariable int id, @RequestHeader HttpHeaders httpheaders, @Valid @RequestBody TopicDto topicDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Topic topic = topicMapper.updateTopicTitleDto(id, topicDto);
            service.update(topic, user);
            return topic;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("/{id}/like")
    public Topic updateLike(@PathVariable int id, @RequestHeader HttpHeaders httpheaders) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Topic topic = service.get(id);
            service.updateLike(topic, user);
            return topic;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @RequestHeader HttpHeaders httpHeaders) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            service.delete(id, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
