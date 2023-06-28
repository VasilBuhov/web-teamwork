package com.company.web.springdemo.services;

import com.company.web.springdemo.exceptions.AuthorizationException;
import com.company.web.springdemo.exceptions.EntityDuplicateException;
import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.Tag;
import com.company.web.springdemo.models.User;
import com.company.web.springdemo.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final String MODIFY_TAGS_ERROR_MESSAGE = "Only admin or tag creator can modify a beer.";

    private final TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tag> get(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String sortOrder) {
        return repository.get(name, minAbv, maxAbv, styleId, sortBy, sortOrder);
    }

    @Override
    public Tag get(int id) {
        return repository.get(id);
    }

    @Override
    public void create(Tag tag, User user) {
        boolean duplicateExists = true;
        try {
            repository.get(tag.getName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Tag", "name", tag.getName());
        }

        tag.setCreatedBy(user);
        repository.create(tag);
    }

    @Override
    public void update(Tag tag, User user) {
        checkModifyPermissions(tag.getId(), user);

        boolean duplicateExists = true;
        try {
            Tag existingTag = repository.get(tag.getName());
            if (existingTag.getId() == tag.getId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Tag", "name", tag.getName());
        }

        repository.update(tag);
    }

    @Override
    public void delete(int id, User user) {
        checkModifyPermissions(id, user);
        repository.delete(id);
    }

    private void checkModifyPermissions(int tagId, User user) {
        Tag tag = repository.get(tagId);
        if (!(user.isAdmin() || tag.getCreatedBy().equals(user))) {
            throw new AuthorizationException(MODIFY_TAGS_ERROR_MESSAGE);
        }
    }

}