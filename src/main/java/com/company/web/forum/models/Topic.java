package com.company.web.forum.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "views")
    private int views;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<Post> posts;
    public Topic(){
    }

    public Topic(int id, User creator, String title, String content, int views, int likes, int dislikes) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.views = views;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creationDate = LocalDateTime.now();
        this.posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
