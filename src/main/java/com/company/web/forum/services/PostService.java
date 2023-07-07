package com.company.web.forum.services;

import com.company.web.forum.models.Post;
import com.company.web.forum.models.User;

import java.util.List;

public interface PostService {
    List<Post> get(int topic, User creator);

    Post get(int id);
    Post get(User creator);

     void update(Post post, User user);

    void create(Post post, User user);

    void delete(int id, User user);

}
