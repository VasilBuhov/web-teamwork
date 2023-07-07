package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private static final String MODIFY_POST_ERROR_MESSAGE = "Only admin or post creator can modify a post.";
    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    public List<Post> get(int topic, User creator) {
        return repository.get(topic, creator);
    }

    @Override
    public Post get(int id) {
        return repository.get(id);
    }

    public Post get(User creator) {
        return repository.get(creator);
    }

    @Override
    public void create(Post post, User user) {
        post.setCreator(user);
        repository.create(post);
    }

    public void update(Post post, User user) {
        checkModifyPermissions(post.getId(), user);
        repository.update(post);
    }

    @Override
    public void delete(int id, User user) {
        checkModifyPermissions(id, user);
        repository.delete(id);
    }

    private void checkModifyPermissions(int postId, User user) {
        Post post = repository.get(postId);
        if (!(user.isAdmin() || post.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
