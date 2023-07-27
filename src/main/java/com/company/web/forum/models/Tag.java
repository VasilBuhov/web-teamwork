package com.company.web.forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.OffsetDateTime;
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "belongs_to")
    private User belongsTo;
    @JsonIgnore
    @Column(name = "status_deleted")
    private int isDeleted;

    @JsonIgnore
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    public Tag() {
    }

    public Tag(int id, String name, User belongsTo, int isDeleted, OffsetDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.belongsTo = belongsTo;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
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

    public User getBelongs_to() {
        return belongsTo;
    }

    public void setBelongs_to(User belongsTo) {
        this.belongsTo = belongsTo;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public User getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(User belongsTo) {
        this.belongsTo = belongsTo;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
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