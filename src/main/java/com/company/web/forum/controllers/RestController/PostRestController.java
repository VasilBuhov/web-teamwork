package com.company.web.forum.controllers.RestController;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.*;
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

    @Autowired
    public PostRestController(PostService service, PostMapper postMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
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
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Post create(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(httpHeaders);
            Post post = postMapper.fromDto(postDto);
            service.create(post, user);
            return post;
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
