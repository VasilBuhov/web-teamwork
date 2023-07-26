package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.*;
import com.company.web.forum.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final String MODIFY_TAGS_ERROR_MESSAGE = "Only admin or tag creator can modify a tag.";

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag getTagById(int id) {
        return tagRepository.get(id);
    }

    @Override
    public List<Tag> get(FilterTagOptions filterTagOptions) {
        return tagRepository.get(filterTagOptions);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.getAllTags();
    }

    @Override
    public void create(String tagName, User belongsToUser) {
        boolean duplicateExists = true;

        try {
            tagRepository.get(tagName);
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        Tag tag = new Tag();
        tag.setName(tagName);

        if (duplicateExists) throw new EntityDuplicateException("Tag", "name", tag.getName());

        tag.setIsDeleted(0);
        tag.setBelongs_to(belongsToUser);
        tagRepository.create(tag);
    }

    @Override
    public void update(Tag tag, User user) {
        checkModifyPermissions(tag.getId(), user);

        boolean duplicateExists = true;
        try {
            Tag existingTag = tagRepository.get(tag.getName());
            if (existingTag.getId() == tag.getId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Tag", "name", tag.getName());
        }

        tagRepository.update(tag);
    }

    @Override
    public void delete(int id, User user) {
        Tag tag = tagRepository.get(id);
        checkModifyPermissions(id, user);
        if (tag.getIsDeleted() == 1) {
            throw new EntityDeletedException("Tag", "ID", String.valueOf(id));
        }
        tagRepository.delete(id);
    }

    private void checkModifyPermissions(int tagId, User user) {
        Tag tag = tagRepository.get(tagId);
        if (!(user.getIsAdmin()==1 || tag.getBelongs_to().equals(user))) {
            throw new AuthorizationException(MODIFY_TAGS_ERROR_MESSAGE);
        }
    }

}