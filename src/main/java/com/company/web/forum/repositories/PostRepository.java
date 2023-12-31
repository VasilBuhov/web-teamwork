package com.company.web.forum.repositories;

import com.company.web.forum.models.*;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository {
    List<Post> get(FilterPostOptions filterPostOptions);

    Post get(int id);

    Post get(String creatorUsername);

    List<Post> getPostsByUser(User user);

    void create(Post post);
    void addPostToTopic(Post post);

    void updateLike(Post post);

    void delete(int id);

    void update(Post post);
}
