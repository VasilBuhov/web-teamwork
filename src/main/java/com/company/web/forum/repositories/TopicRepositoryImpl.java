package com.company.web.forum.repositories;


import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TopicRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Topic> get(FilterTopicOptions filterTopicOptions) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterTopicOptions.getCreatorUsername().ifPresent(value -> {
                filters.add("creator.username = :creatorUsername");
                params.put("creatorUsername", value);
            });
            filterTopicOptions.getTagTitle().ifPresent(value -> {
                filters.add("tag.title = :tagTitle");
                params.put("tagTitle", value);
            });
            filterTopicOptions.getTitle().ifPresent(value -> {
                filters.add("title like :title");
                params.put("title", String.format("%%%s%%", value));
            });
            filterTopicOptions.getMinCreationDate().ifPresent(value -> {
                filters.add("creationDate >= :minCreationDate");
                params.put("minCreationDate", value);
            });

            filterTopicOptions.getMaxCreationDate().ifPresent(value -> {
                filters.add("creationDate <= :maxCreationDate");
                params.put("maxCreationDate", value);
            });

            StringBuilder queryString = new StringBuilder("from Topic");

            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }
            queryString.append(generateOrderBy(filterTopicOptions));

            Query<Topic> query = session.createQuery(queryString.toString(), Topic.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public Topic get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Topic topic = session.get(Topic.class, id);
            if (topic == null) {
                throw new EntityNotFoundException("Topic", id);
            }
            return topic;
        }
    }

    public Topic get(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Topic> query = session.createQuery("from Topic where title = :title", Topic.class);
            query.setParameter("title", title);

            List<Topic> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Topic", "title", title);
            }
            //result.get(0).setViews(result.get(0).getViews() + 1);
            return result.get(0);
        }
    }

    @Override
    public void create(Topic topic) {
        try (Session session = sessionFactory.openSession()) {
            session.save(topic);
        }
    }

    @Override
    public void update(Topic topic) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(topic);
            session.getTransaction().commit();
        }
    }

    public void updateLike(Topic topic) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(topic);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Topic topicToDelete = get(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(topicToDelete);
            session.getTransaction().commit();
        }
    }

    private String generateOrderBy(FilterTopicOptions filterTopicOptions) {
        if (filterTopicOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (filterTopicOptions.getSortBy().get()) {
            case "creation date":
                orderBy = "creationDate";
                break;
            case "likes":
                orderBy = "likes";
                break;
            case "title":
                orderBy = "title";
                break;
        }

        orderBy = String.format(" order by %s", orderBy);

        if (filterTopicOptions.getSortOrder().isPresent() && filterTopicOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }

        return orderBy;
    }
}
