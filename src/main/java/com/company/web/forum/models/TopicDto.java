package com.company.web.forum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class TopicDto {
    @NotNull(message = "Title can't be empty.")
    @Size(min = 16, max = 64, message = "Name should be between 16 and 64 symbols.")
    private String title;
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols.")
    private String content;
    @NotNull(message = "Tag can't be empty.")
    private Tag tag;
    @NotNull(message = "Topic must have creator.")
    private User creator;
    @Positive(message = "Views count can't be negative.")
    private int views;
    @Positive(message = "Likes count can't be negative.")
    private int likes;
    @Positive(message = "Dislikes count can't be negative.")
    private int dislikes;
    @NotNull(message = "Topic must have creation date.")
    private LocalDateTime creationDate;
    private List<Post> posts;

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

    public Tag getTag() {
        return tag;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setTag(Tag tag) {
        this.tag = tag;

    }
}
