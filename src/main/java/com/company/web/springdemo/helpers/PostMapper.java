package com.company.web.springdemo.helpers;


import com.company.web.springdemo.models.Post;
import com.company.web.springdemo.models.PostDto;
import com.company.web.springdemo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final PostService postService;
@Autowired
    public PostMapper(PostService postService) {
        this.postService = postService;
    }
    public Post fromDto(PostDto dto) {
    Post post = new Post();
    post.setTitle(dto.getTitle());
    post.setContent(dto.getContent());
    post.setCreator(dto.getCreator());
    return post;
    }
    public Post fromDto(int id, PostDto dto) {
    Post post = fromDto(dto);
    post.setId(id);
    Post repositoryPost = postService.get(id);
    post.setCreator(repositoryPost.getCreator());
    post.setViews(repositoryPost.getViews());
    post.setCreationDate(repositoryPost.getCreationDate());
    post.setLastActivity(repositoryPost.getLastActivity());
    post.setLikes(repositoryPost.getLikes());
    post.setDislikes(repositoryPost.getDislikes());
    if (id == 1) {
        post.setComments(repositoryPost.getComments());
    }
    return post;
    }
}
