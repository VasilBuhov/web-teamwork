package com.company.web.springdemo.services;

import com.company.web.springdemo.models.Post;
import com.company.web.springdemo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository repository;
@Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    public List<Post> get() {
    return repository.get();
    }
    @Override
    public Post get(int id) {
        return repository.get(id);
    }

    @Override
    public void update(Post post) {
    repository.update(post);
    }

    @Override
    public void create(Post post) {
    repository.create(post);
    }

    @Override
    public void delete(int id) {
    repository.delete(id);
    }
}
