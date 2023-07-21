package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.FilterTagOptions;
import com.company.web.forum.models.Tag;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tag> get(FilterTagOptions filterTagOptions) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterTagOptions.getCreator().ifPresent(value -> {
                filters.add("creator.id = :creatorId");
                params.put("creatorId", value);
            });
            filterTagOptions.getTag().ifPresent(value -> {
                filters.add("tag.id = :tagId");
                params.put("tagId", value);
            });
            StringBuilder queryString = new StringBuilder("from Topic");
            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }
            Query<Tag> query = session.createQuery(queryString.toString(), Tag.class);
            query.setProperties(params);
            return query.list();
        }
    }

    public List<Tag> filter(List<Tag> tags, String name, int belongs_to) {
        tags = filterByName(tags, name);
        tags = sortBy(tags, String.valueOf(belongs_to));
        tags = order(tags, String.valueOf(belongs_to));
        return tags;
    }

    @Override
    public Tag get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Tag tag = session.get(Tag.class, id);
            if (tag == null) {
                throw new EntityNotFoundException("REPO: Tag", id);

            }
            if (tag.getIsDeleted()==1) throw new EntityDeletedException("Tag", "id", String.valueOf(id));//maybe return hard-coded object of default tag?
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
    public List<Tag> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag where name = :name and isDeleted = 0", Tag.class);
            query.setParameter("name", name);

            List<Tag> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Tag", "name", name);
            }
            return result;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
            Root<Tag> tagRoot = cq.from(Tag.class);
            cq.select(tagRoot);
            return session.createQuery(cq).list();
        } catch (Exception e) {
            // Handle exceptions
            return new ArrayList<>();
        }
    }

    @Override
    public void create(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();
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
        tagToDelete.setIsDeleted(1);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(tagToDelete);
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

    private static List<Tag> sortBy(List<Tag> tags, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    tags.sort(Comparator.comparing(Tag::getName));
                    break;
                case "belongs_to":
//                    tags.sort(Comparator.comparing(Tag::getBelongs_to));
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