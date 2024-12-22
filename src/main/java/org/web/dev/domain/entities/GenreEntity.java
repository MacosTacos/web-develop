package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "genres")
public class GenreEntity extends BaseEntity {

    private String name;
    private boolean isDeleted;
    private List<BookEntity> bookEntities;

    protected GenreEntity() {
    }

    public GenreEntity(String name) {
        this.name = name;
        this.isDeleted = false;
    }

    public String getName() {
        return name;
    }

    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "genreEntities", fetch = FetchType.LAZY)
    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }
}
