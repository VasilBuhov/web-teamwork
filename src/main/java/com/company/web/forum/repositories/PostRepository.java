package com.company.web.forum.repositories;

import com.company.web.forum.models.Post;
import com.company.web.forum.models.User;

import java.util.List;

public interface PostRepository {
    List<Post> get();
    Post get(int id);
    Post get(User creator);
    void create(Post post);
    void delete(int id);
    void update(Post post);
}
