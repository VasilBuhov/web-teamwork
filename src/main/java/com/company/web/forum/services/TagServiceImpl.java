package com.company.web.forum.services;

import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.TagRepository;
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
    public List<Tag> get(String name, int belongs_to, String sortBy) {
        return repository.get(name, belongs_to, sortBy);
    }

    @Override
    public Tag get(int id) {
        return repository.get(id);
    }

    @Override
    public void create(Tag tag, int user) {
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
//        TODO: fix check permissions
//        if (!(user.isAdmin() || tag.getCreatedBy().equals(user))) {
//            throw new AuthorizationException(MODIFY_TAGS_ERROR_MESSAGE);
//        }
    }

}