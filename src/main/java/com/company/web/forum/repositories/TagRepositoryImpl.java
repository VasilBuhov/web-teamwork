package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Tag;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tag> get(String name, int belongs_to, String sortBy) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag", Tag.class);
            List<Tag> tags = query.list();
            return filter(tags, name, belongs_to);
        }
    }

    public List<Tag> filter(List<Tag> tags, String name, int belongs_to) {
        tags = filterByName(tags, name);
        tags = filterByBelonging(tags, String.valueOf(belongs_to));
        tags = sortBy(tags, String.valueOf(belongs_to));
        tags = order(tags, String.valueOf(belongs_to));
        return tags;
    }

    @Override
    public Tag get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Tag tag = session.get(Tag.class, id);
            if (tag == null) {
                throw new EntityNotFoundException("Tag", id);
            }
            return tag;
        }
    }

    @Override
    public Tag get(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag where name = :name", Tag.class);
            query.setParameter("name", name);

            List<Tag> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Tag", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public void create(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.save(tag);
        }
    }

    @Override
    public void update(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Tag tagToDelete = get(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(tagToDelete);
            session.getTransaction().commit();
        }
    }

    private static List<Tag> filterByName(List<Tag> tags, String name) {
        if (name != null && !name.isEmpty()) {
            tags = tags.stream()
                    .filter(tag -> containsIgnoreCase(tag.getName(), name))
                    .collect(Collectors.toList());
        }
        return tags;
    }

    private static List<Tag> filterByBelonging(List<Tag> tags, String content) {
        if (content != null) {
            tags = tags.stream()
                    .filter(tag -> tag.getContent().equals(content))
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
                    tags.sort(Comparator.comparing(Tag::getContent));
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