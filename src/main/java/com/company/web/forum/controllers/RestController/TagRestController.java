package com.company.web.forum.controllers.RestController;

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

    private final TagService tagService;
    private final TagMapper tagMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TagRestController(TagService tagService, TagMapper tagMapper, AuthenticationHelper authenticationHelper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return tagMapper.toDtoList(tags);
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable int id) {
        try {
            Tag tag = tagService.getTagById(id);
            return tagMapper.toDto(tag);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("REST: Tag", "id", String.valueOf(id));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ID cannot be converted into string");
        }
    }

    @GetMapping("/filter/{tag}")
    public List<Tag> get(
            @RequestParam(required = false) User creator,
            @RequestParam(required = false) Tag tag) {
        FilterTagOptions filterTagOptions = new FilterTagOptions(creator, tag);
        return tagService.get(filterTagOptions);
    }

    @PostMapping
    public Tag create(@RequestHeader HttpHeaders headers, @Valid @RequestBody TagDto tagDto) {
        try {
            User belongsToUser = authenticationHelper.tryGetUser(headers);
            String tagName = tagMapper.fromDto(tagDto).getName();
            tagService.create(tagName, belongsToUser);
            return tagService.getTagById(tagMapper.fromDto(tagDto).getId());
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
            tagService.update(tag, user);
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
            tagService.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
