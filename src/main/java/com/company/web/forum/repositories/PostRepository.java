package com.company.web.forum.repositories;

import com.company.web.forum.models.Post;

import java.util.List;

public interface PostRepository {
    List<Post> get();
    Post get(int id);
    void create(Post post);
    void delete(int id);
    void update(Post post);
}
