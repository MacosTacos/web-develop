package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class AuthorEntity extends BaseEntity {

    private String name;
    private String description;
    private boolean isDeleted;
    private List<BookEntity> bookEntities;

    protected AuthorEntity() {
    }

    public AuthorEntity(String name, String description) {
        this.name = name;
        this.description = description;
        this.isDeleted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @ManyToMany(mappedBy = "authorEntities", fetch = FetchType.LAZY)
    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }
}
