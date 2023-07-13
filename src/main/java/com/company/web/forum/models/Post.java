package com.company.web.forum.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @Column(name = "id")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "id")
    private User creator;
    @Column(name = "content")
    private String content;
    @Column(name = "views")
    private int views;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;

    public Post() {
    }

    public Post(int id, Topic topic, User creator, String content, int views, LocalDateTime creationDate, int likes, int dislikes) {
        this.id = id;
        this.topic = topic;
        this.creator = creator;
        this.content = content;
        this.views = views;
        this.creationDate = creationDate;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
