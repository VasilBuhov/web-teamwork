package com.company.web.forum.controllers.RestController;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.*;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import com.company.web.forum.helpers.AuthenticationHelper;

import com.company.web.forum.helpers.PostMapper;

import com.company.web.forum.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService service;
    private final PostMapper postMapper;
    private final AuthenticationHelper authenticationHelper;
    private final TopicService topicService;

    @Autowired
    public PostRestController(PostService service, PostMapper postMapper, AuthenticationHelper authenticationHelper, TopicService topicService) {
        this.service = service;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
        this.topicService = topicService;
    }

    @GetMapping
    public List<Post> get(
            @RequestParam(required = false) String creatorUsername,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        FilterPostOptions filterPostOptions = new FilterPostOptions(creatorUsername, sortBy, sortOrder);
        return service.get(filterPostOptions);
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable int id) {
        try {
            Post post = service.get(id);
            return postMapper.toDto(post);
        } catch (EntityNotFoundException | EntityDeletedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public PostDto create(@PathVariable int id, @RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            Post post = postMapper.CreatePostDto(postDto);
            Topic topic = topicService.get(id);
            service.create(post, user, topic);
            return postMapper.toDto(post);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable int id, @RequestHeader HttpHeaders httpheaders, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Post post = postMapper.fromDto(id, postDto);
            service.update(post, user);
            return post;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException | EntityDeletedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("/{id}/like")
    public Post updateLike(@PathVariable int id, @RequestHeader HttpHeaders httpheaders) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Post post = service.get(id);
            service.updateLike(post, user);
            return post;
        } catch (EntityNotFoundException | EntityDeletedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @RequestHeader HttpHeaders httpHeaders) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            Post postToBeDeleted = service.get(id);
            Topic topic = postToBeDeleted.getTopic();
            service.delete(id, user, postToBeDeleted, topic);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException | EntityDeletedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
