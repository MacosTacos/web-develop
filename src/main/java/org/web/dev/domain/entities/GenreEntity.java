package org.web.dev.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "genres")
public class GenreEntity extends BaseEntity {

    private String name;
    private List<BookEntity> bookEntities;

    protected GenreEntity() {
    }

    public GenreEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
