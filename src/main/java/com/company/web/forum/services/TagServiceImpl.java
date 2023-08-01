package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.*;
import com.company.web.forum.repositories.TagRepository;
import com.company.web.forum.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final String MODIFY_TAGS_ERROR_MESSAGE = "Only admin or tag creator can modify a tag.";
    private static final String MODIFY_TOPIC_ERROR_MESSAGE = "Only admin or topic creator can modify a topic.";

    private final TagRepository tagRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TopicRepository topicRepository) {
        this.tagRepository = tagRepository;
        this.topicRepository = topicRepository;
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
    public List<Tag> getTopTags() {
        return tagRepository.getTopTags();
    }

    @Override
    public List<Tag> getAllTags(Integer page, Integer size) {
        return tagRepository.getAllTags(page, size);
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
        tag.setCreationDate(OffsetDateTime.now());
        tagRepository.create(tag);
    }

public void createTagForTopic(String tagName, User belongsToUser, Topic topic) {
    checkModifyTopicPermissions(topic.getId(), belongsToUser);

    boolean tagExists = topic.getTags().stream()
            .anyMatch(tag -> tagName.equals(tag.getName()));
    if (tagExists) {
        throw new EntityDuplicateException("Tag", "name", tagName);
    }

    Tag tag = new Tag();
        tag.setName(tagName);
        tag.setIsDeleted(0);
        tag.setBelongs_to(belongsToUser);
        tag.setCreationDate(OffsetDateTime.now());
        tagRepository.create(tag);
        topic.getTags().add(tag);
        topicRepository.update(topic);
}

    public void removeTagFromTopic(String tagName, User user, Topic topic) {
        checkModifyTopicPermissions(topic.getId(), user);
        boolean removed = topic.getTags().removeIf(tag -> tagName.equals(tag.getName()));

        if (!removed) {
            throw new EntityNotFoundException("Tag", "name", tagName);
        }

        topicRepository.update(topic);
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

    @Override
    public Page<Tag> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Tag> tags = getAllTags();
        List<Tag> list;

        if (tags.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, tags.size());
            list = tags.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tags.size());
    }

    private void checkModifyTopicPermissions(int topicId, User user) {
        Topic topic = topicRepository.get(topicId);
        if (!(user.getIsAdmin() == 1 || topic.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_TOPIC_ERROR_MESSAGE);
        }
    }
}