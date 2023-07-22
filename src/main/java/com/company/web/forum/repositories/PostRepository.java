package com.company.web.forum.repositories;

import com.company.web.forum.models.*;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository {
    List<Post> get(FilterPostOptions filterPostOptions);

    Post get(int id);

    Post get(String creatorUsername);

    void create(Post post);

    void addLike(Post post);

    void removeLike(Post post);

    void delete(int id);

    void update(Post post);
}
