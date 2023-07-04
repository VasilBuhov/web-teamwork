package com.company.web.forum.models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    private String content;

    @Column(name = "belongs_to")
    private int belongs_to;


    @Column(name = "created_by")
    private int createdBy;

    public Tag() {
    }

    public Tag(int id, String name, String content, int belongs_to, int createdBy) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.belongs_to = belongs_to;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBelongs_to() {
        return belongs_to;
    }

    public void setBelongs_to(int belongs_to) {
        this.belongs_to = belongs_to;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id == tag.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}