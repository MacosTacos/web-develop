package org.web.dev.domain.entities;

import jakarta.persistence.*;
import org.web.dev.exceptions.book.BookRemovalException;
import org.web.dev.exceptions.book.InvalidNumberException;
import org.web.dev.exceptions.book.NameIsNullException;

import java.util.List;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    private String name;
    private Integer publicationYear;
    private String description;
    private boolean isDeleted;
    private int quantity;
    private Double price;
    private List<GenreEntity> genreEntities;
    private List<AuthorEntity> authorEntities;
    private List<OrderContentEntity> orderContentEntities;

    protected BookEntity() {
    }

    public BookEntity(String name, Integer publicationYear, Double price, List<GenreEntity> genreEntities, List<AuthorEntity> authorEntities) {
        this.name = name;
        this.publicationYear = publicationYear;
        this.price = price;
        this.genreEntities = genreEntities;
        this.authorEntities = authorEntities;
    }

    public void setOrderContentEntities(List<OrderContentEntity> orderContentEntities) {
        this.orderContentEntities = orderContentEntities;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "publication_year", nullable = false)
    public Integer getPublicationYear() {
        return publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "id_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        if (!deleted) {
            isDeleted = true;
        } else {
            throw new BookRemovalException("Book is deleted");
        }

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

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity += quantity;
        } else {
            throw new InvalidNumberException("quantity can not be less than 0");
        }
    }

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new InvalidNumberException("price can not be less than 0");
        }
    }
}
