package com.company.web.forum.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "topic")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "creator")
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_likedBy",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<User> likedBy;

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
        this.likedBy = new HashSet<>();
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

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
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
