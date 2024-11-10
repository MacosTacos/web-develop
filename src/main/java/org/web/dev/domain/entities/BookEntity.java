package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    private String name;
    private String publicationYear;
    private String description;
    private List<GenreEntity> genreEntities;
    private List<AuthorEntity> authorEntities;
    private List<OrderContentEntity> orderContentEntities;

    protected BookEntity() {
    }

    public BookEntity(String name, String publicationYear, String description, List<GenreEntity> genreEntities, List<AuthorEntity> authorEntities) {
        this.name = name;
        this.publicationYear = publicationYear;
        this.description = description;
        this.genreEntities = genreEntities;
        this.authorEntities = authorEntities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "publication_year")
    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    public List<GenreEntity> getGenreEntities() {
        return genreEntities;
    }

    public void setGenreEntities(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    public List<AuthorEntity> getAuthorEntities() {
        return authorEntities;
    }

    public void setAuthorEntities(List<AuthorEntity> authorEntities) {
        this.authorEntities = authorEntities;
    }

    @OneToMany(mappedBy = "bookEntity", fetch = FetchType.LAZY)
    public List<OrderContentEntity> getOrderContentEntities() {
        return orderContentEntities;
    }

    public void setOrderContentEntities(List<OrderContentEntity> orderContentEntities) {
        this.orderContentEntities = orderContentEntities;
    }
}
