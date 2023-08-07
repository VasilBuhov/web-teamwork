package com.company.web.forum.services;

import com.company.web.forum.models.FilterPostOptions;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;

import java.util.List;

public interface PostService {
    List<Post> get(FilterPostOptions filterPostOptions);

    Post get(int id);

    Post get(String creatorUsername);

    void update(Post post, User user);

    void updateLike(Post post, User user);

    List<Post> getPostsByUser(User user);

    void create(Post post, User user, Topic topic);

    void delete(int id, User user, Post post, Topic topic);

}
