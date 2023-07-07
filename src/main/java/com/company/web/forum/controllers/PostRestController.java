package com.company.web.forum.controllers;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import com.company.web.forum.helpers.AuthenticationHelper;

import com.company.web.forum.helpers.PostMapper;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.PostDto;
import com.company.web.forum.models.User;

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

    public PostRestController(PostService service, PostMapper postMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Post> get(
            @RequestParam(required = false) int topic,
            @RequestParam(required = false) User creator) {
        return service.get(topic, creator);
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping
    public void create(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            Post post = postMapper.fromDto(postDto);
            service.create(post, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestHeader HttpHeaders httpheaders, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpheaders);
            Post post = postMapper.fromDto(postDto);
            service.update(post, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @RequestHeader HttpHeaders httpHeaders) {
        try{
            User user = authenticationHelper.tryGetUser(httpHeaders);
            service.delete(id, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
