package com.company.web.forum.helpers;


import com.company.web.forum.models.Post;
import com.company.web.forum.models.PostDto;
import com.company.web.forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    public Post updatePostDto(PostDto dto, int id) {
        Post post = postService.get(id);
        post.setContent(dto.getContent());
        return post;
    }

    public PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setCreator(post.getCreator().getUsername());
        postDto.setContent(post.getContent());
        postDto.setTopic(post.getTopic().getTitle());
        postDto.setLikes(post.getLikes());
        postDto.setCreationDate(post.getCreationDate());
        return postDto;
    }
    public List<PostDto> postDtoList (List<Post> posts) {
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
