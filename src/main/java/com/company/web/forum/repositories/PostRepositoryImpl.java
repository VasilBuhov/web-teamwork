package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.Tag;
import com.company.web.forum.models.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> get(int topic, User creator) {

        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post", Post.class);
            List<Post> posts = query.list();
            return filter(posts, topic, creator);
        }
    }
    public List<Post> filter(List<Post> posts, int topic, User creator) {
        posts = filterByTopic(posts, topic);
        posts = filterByCreator(posts, creator);
        return posts;
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

    public Post get(User creator) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where creator = :creator", Post.class);
            query.setParameter("creator", creator);

            List<Post> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Post", "creator", creator.getUsername());
            }
            return result.get(0);
        }
    }

    @Override
    public void create(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.save(post);
        }
    }

    @Override
    public void delete(int id) {
        Post postToDelete = get(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(postToDelete);
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

 private static List<Post> filterByTopic(List<Post> posts, int topic) {
        if (topic >= 0) {
            posts = posts.stream()
                    .filter(post -> post.getTopic() == topic)
                    .collect(Collectors.toList());
        }
        return posts;
    }
    private static List<Post> filterByCreator(List<Post> posts, User creator) {
        if (creator != null) {
            posts = posts.stream()
                    .filter(post -> post.getCreator().equals(creator))
                    .collect(Collectors.toList());
        }
        return posts;
    }
}
