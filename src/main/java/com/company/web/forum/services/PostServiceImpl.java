package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.helpers.ParseSmileys;
import com.company.web.forum.models.*;
import com.company.web.forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private static final String MODIFY_POST_ERROR_MESSAGE = "Only admin or post creator can modify a post.";
    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    public List<Post> get(FilterPostOptions filterPostOptions) {
        List<Post> resultList = repository.get(filterPostOptions);
        for (Post post : resultList) post.setContent(ParseSmileys.replaceSmileys(post.getContent()));
        return resultList;
    }

    @Override
    public Post get(int id) {
        Post post = repository.get(id);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        post.setContent(ParseSmileys.replaceSmileys(post.getContent()));
        return post;
    }

    public Post get(String creatorUsername) {
        Post post = repository.get(creatorUsername);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        post.setContent(ParseSmileys.replaceSmileys(post.getContent()));
        return post;
    }
    @Override
    public List<Post> getPostsByUser(User user) {
        List<Post> resultList = repository.getPostsByUser(user);
        for (Post post : resultList) post.setContent(ParseSmileys.replaceSmileys(post.getContent()));
        return resultList;
    }

    @Override
    public void create(Post post, User user, Topic topic) {
        post.setCreator(user);
        post.setCreationDate(LocalDateTime.now());
        post.setLikes(0);
        post.setTopic(topic);
        post.setStatusDeleted(0);
        topic.getPosts().add(post);
        repository.create(post);
    }
    public void update(Post post, User user) {
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        checkModifyPermissions(post.getId(), user);
        repository.update(post);
    }

    @Override
    public void updateLike(Post post, User user) {
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        if (post.getLikedBy().contains(user)) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedBy().remove(user);
            repository.updateLike(post);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedBy().add(user);
            repository.updateLike(post);
        }
    }

    @Override
    public void delete(int id, User user, Post post, Topic topic) {
        checkModifyPermissions(id, user);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        topic.getPosts().remove(post);
        repository.delete(id);
    }

    private void checkModifyPermissions(int postId, User user) {
        Post post = repository.get(postId);
        if (!(user.getIsAdmin() == 1 || post.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
