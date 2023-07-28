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
        Post post = CreatePostDto(dto);
        post.setId(id);
        Post repositoryPost = postService.get(id);
        post.setCreationDate(repositoryPost.getCreationDate());
        post.setLikes(repositoryPost.getLikes());
        return post;
    }

    public Post CreatePostDto(PostDto dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        return post;
    }

    public PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setCreatorUsername(post.getCreator().getUsername());
        postDto.setContent(post.getContent());
        postDto.setLikes(post.getLikes());
        postDto.setCreationDate(post.getCreationDate());
        return postDto;
    }
}
