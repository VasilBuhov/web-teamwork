package com.company.web.forum.controllers;


import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.TagMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController

@RequestMapping("/api/tags")
public class TagRestController {

    private final TagService service;
    private final TagMapper tagMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TagRestController(TagService service, TagMapper tagMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.tagMapper = tagMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Tag> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int belongs_to,
            @RequestParam(required = false) String sortBy) {
        return service.get(name, belongs_to, sortBy);
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable int id) {
        try {
            return service.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Tag create(@RequestHeader HttpHeaders headers, @Valid @RequestBody TagDto tagDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            int user_id = user.getId();
            Tag tag = tagMapper.fromDto(tagDto);
            service.create(tag, user_id);
            return tag;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Tag update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody TagDto tagDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Tag tag = tagMapper.fromDto(id, tagDto);
            service.update(tag, user);
            return tag;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
