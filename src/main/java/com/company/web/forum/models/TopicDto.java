package com.company.web.forum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TopicDto {
    @Size(min = 16, max = 64, message = "Name should be between 16 and 64 symbols.")
    private String title;
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols.")
    private String content;

    private Set<String> tagNames;
    private String creatorUsername;

    private int views;

    private int likes;

    private LocalDateTime creationDate;
    private Set<Post> postsList;

    public TopicDto() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Post> getPosts() {
        return postsList;
    }

    public void setPosts(Set<Post> posts) {
        this.postsList = posts;
    }


    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public Set<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(Set<String> tagNames) {
        this.tagNames = tagNames;
    }
}
