package com.company.web.forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "likes")
    private int likes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_likedBy",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> likedBy;
    @JsonIgnore
    @Column(name = "status_deleted")
    private int statusDeleted;

    public Post() {
    }

    public Post(int id, Topic topic, User creator, String content, LocalDateTime creationDate, int likes, int statusDeleted) {
        this.id = id;
        this.topic = topic;
        this.creator = creator;
        this.content = content;
        this.creationDate = creationDate;
        this.likes = likes;
        this.likedBy = new HashSet<>();
        this.statusDeleted = 0;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public int getStatusDeleted() {
        return statusDeleted;
    }

    public void setStatusDeleted(int statusDeleted) {
        this.statusDeleted = statusDeleted;
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
