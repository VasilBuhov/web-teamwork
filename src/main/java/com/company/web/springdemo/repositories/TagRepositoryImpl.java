package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final List<Tag> tags;

    @Autowired
    public TagRepositoryImpl(StyleRepository styleRepository, UserRepository userRepository) {
        tags = new ArrayList<>();
        Tag tag = new Tag(1, "Glarus English Ale", 4.6);
        tag.setStyle(styleRepository.get(1));
        tag.setCreatedBy(userRepository.getUserById(1));
        tags.add(tag);

        tag = new Tag(2, "Rhombus Porter", 5.0);
        tag.setStyle(styleRepository.get(2));
        tag.setCreatedBy(userRepository.getUserById(1));
        tags.add(tag);

        tag = new Tag(3, "Opasen Char", 6.6);
        tag.setStyle(styleRepository.get(3));
        tag.setCreatedBy(userRepository.getUserById(2));
        tags.add(tag);
    }

    @Override
    public List<Tag> get(String name, Double minAbv, Double maxAbv, Integer styleId, String sortBy, String sortOrder) {
        List<Tag> result = tags;
        result = filterByName(result, name);
        result = filterByAbv(result, minAbv, maxAbv);
        result = filterByStyle(result, styleId);
        result = sortBy(result, sortBy);
        result = order(result, sortOrder);
        return result;
    }

    @Override
    public Tag get(int id) {
        return tags.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Tag", id));
    }

    @Override
    public Tag get(String name) {
        return tags.stream()
                .filter(beer -> beer.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Tag", "name", name));
    }

    @Override
    public void create(Tag tag) {
        int nextId = tags.size() + 1;
        tag.setId(nextId);
        tags.add(tag);
    }

    @Override
    public void update(Tag tag) {
        Tag tagToUpdate = get(tag.getId());
        tagToUpdate.setName(tag.getName());
        tagToUpdate.setAbv(tag.getAbv());
        tagToUpdate.setStyle(tag.getStyle());
    }

    @Override
    public void delete(int id) {
        Tag tagToDelete = get(id);
        tags.remove(tagToDelete);
    }

    private static List<Tag> filterByName(List<Tag> tags, String name) {
        if (name != null && !name.isEmpty()) {
            tags = tags.stream()
                    .filter(tag -> containsIgnoreCase(tag.getName(), name))
                    .collect(Collectors.toList());
        }
        return tags;
    }

    private static List<Tag> filterByAbv(List<Tag> tags, Double minAbv, Double maxAbv) {
        if (minAbv != null) {
            tags = tags.stream()
                    .filter(tag -> tag.getAbv() >= minAbv)
                    .collect(Collectors.toList());
        }

        if (maxAbv != null) {
            tags = tags.stream()
                    .filter(tag -> tag.getAbv() <= maxAbv)
                    .collect(Collectors.toList());
        }

        return tags;
    }

    private static List<Tag> filterByStyle(List<Tag> tags, Integer styleId) {
        if (styleId != null) {
            tags = tags.stream()
                    .filter(tag -> tag.getStyle().getId() == styleId)
                    .collect(Collectors.toList());
        }
        return tags;
    }

    private static List<Tag> sortBy(List<Tag> tags, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    tags.sort(Comparator.comparing(Tag::getName));
                    break;
                case "abv":
                    tags.sort(Comparator.comparing(Tag::getAbv));
                case "style":
                    tags.sort(Comparator.comparing(beer -> beer.getStyle().getName()));
                    break;
            }
        }
        return tags;
    }

    private static List<Tag> order(List<Tag> tags, String order) {
        if (order != null && !order.isEmpty()) {
            if (order.equals("desc")) {
                Collections.reverse(tags);
            }
        }
        return tags;
    }

    private static boolean containsIgnoreCase(String value, String sequence) {
        return value.toLowerCase().contains(sequence.toLowerCase());
    }

}