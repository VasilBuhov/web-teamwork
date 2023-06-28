package com.company.web.springdemo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private final int id;
    private final User creator;
    private String title;
    private String content;
    private int views;
    private final LocalDateTime creationDate;
    private LocalDateTime lastActivity;
    private int likes;
    private int dislikes;
    private int comments = 0;

    public Post(int id, boolean isHeadPost, User creator, String title, String content, int views, LocalDateTime creationDate, LocalDateTime lastActivity, int likes, int dislikes) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.views = views;
        this.creationDate = creationDate;
        this.lastActivity = lastActivity;
        this.likes = likes;
        this.dislikes = dislikes;
        if (isHeadPost) {
            List<Post> commentsList = new ArrayList<>();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
