package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> get(FilterPostOptions filterPostOptions) {

        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filters.add("status_deleted = :statusDeleted");
            params.put("statusDeleted", 0);

            filterPostOptions.getCreatorUsername().ifPresent(value -> {
                filters.add("creator.username = :creatorUsername");
                params.put("creatorUsername", value);
            });

            String queryString = "from Post" +
                    " where " +
                    String.join(" and ", filters) +
                    generateOrderBy(filterPostOptions);

            org.hibernate.query.Query<Post> query = session.createQuery(queryString, Post.class);
            query.setProperties(params);

            //query.setMaxResults(10);

            return query.list();
        }
    }

    @Override
    public Post get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post", id);
            }
            return post;
        }
    }

    public Post get(String creatorUsername) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where creator.username = :creatorUsername", Post.class);
            query.setParameter("creatorUsername", creatorUsername);

            List<Post> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Post", "creator", creatorUsername);
            }
            return result.get(0);
        }
    }
    @Override
    public List<Post> getPostsByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("FROM Post WHERE creator = :user", Post.class);
            query.setParameter("user", user);
            return query.list();
        }
    }



    @Override
    public void create(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.save(post);
        }
    }
    public void addPostToTopic(Post post) {

    }
    @Override
    public void delete(int id) {
        Post postToDelete = get(id);
        postToDelete.setStatusDeleted(1);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(postToDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        }
    }
    public void updateLike(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        }
    }

    private String generateOrderBy(FilterPostOptions filterPostOptions) {
        if (filterPostOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (filterPostOptions.getSortBy().get()) {
            case "creation date":
                orderBy = "creationDate";
                break;
            case "likes":
                orderBy = "likes";
                break;
        }

        orderBy = String.format(" order by %s", orderBy);

        if (filterPostOptions.getSortOrder().isPresent() && filterPostOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }

        return orderBy;
    }
}
