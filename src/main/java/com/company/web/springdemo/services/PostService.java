package com.company.web.springdemo.services;

import com.company.web.springdemo.models.Beer;
import com.company.web.springdemo.models.Post;
import com.company.web.springdemo.models.User;

import java.util.List;

public interface PostService {
    public Post get(int id);
    public void update(Post post);
    void create(Post post);
    void delete(int id);

}
