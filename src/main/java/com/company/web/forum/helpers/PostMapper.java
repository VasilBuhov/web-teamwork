package com.company.web.forum.helpers;


import com.company.web.forum.models.Post;
import com.company.web.forum.models.PostDto;
import com.company.web.forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final PostService postService;

    @Autowired
    public PostMapper(PostService postService) {
        this.postService = postService;
    }

    public Post fromDto(int id, PostDto dto) {
        Post post = fromDto(dto);
        post.setId(id);
        Post repositoryPost = postService.get(id);
        post.setViews(repositoryPost.getViews());
        post.setCreationDate(repositoryPost.getCreationDate());
        post.setLikes(repositoryPost.getLikes());
        post.setDislikes(repositoryPost.getDislikes());
        return post;
    }

    public Post fromDto(PostDto dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setCreator(dto.getCreator());
        return post;
    }

}
