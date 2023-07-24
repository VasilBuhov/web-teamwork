package com.company.web.forum.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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
    @ManyToOne
    @JoinColumn(name = "tag")
    private Tag tag;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "views")
    private int views;
    @Column(name = "likes")
    private int likes;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "posts_list")
    private List<Post> posts;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "topic_likedBy",
            joinColumns = {@JoinColumn(name = "topic_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> likedBy;
    public Topic(){
    }

    public Topic(int id, User creator, String title, String content, int views, int likes) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.content = content;
        this.views = views;
        this.likes = likes;
        this.creationDate = LocalDateTime.now();
        this.posts = new ArrayList<>();
        this.likedBy = new HashSet<>();
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id == topic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
