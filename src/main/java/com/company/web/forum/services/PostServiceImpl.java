package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
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
        return repository.get(filterPostOptions);
    }

    @Override
    public Post get(int id) {
        return repository.get(id);
    }

    public Post get(String creatorUsername) {
        return repository.get(creatorUsername);
    }

    @Override
    public void create(Post post, User user, Topic topic) {
        post.setCreator(user);
        post.setCreationDate(LocalDateTime.now());
        post.setLikes(0);
        post.setTopic(topic);
        topic.getPosts().add(post);
        repository.create(post);
    }
    public void update(Post post, User user) {
        checkModifyPermissions(post.getId(), user);
        repository.update(post);
    }

    @Override
    public void updateLike(Post post, User user) {
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
