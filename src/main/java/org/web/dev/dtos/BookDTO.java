package org.web.dev.dtos;

import java.io.Serializable;
import java.util.List;

public class BookDTO implements Serializable {
    private Long id;
    private String name;
    private Integer publicationYear;
    private String description;
    private int quantity;
    private Double price;
    private List<GenreDTO> genreDTOS;
    private List<AuthorDTO> authorDTOS;

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<GenreDTO> getGenreDTOS() {
        return genreDTOS;
    }

    public void setGenreDTOS(List<GenreDTO> genreDTOS) {
        this.genreDTOS = genreDTOS;
    }

    public List<AuthorDTO> getAuthorDTOS() {
        return authorDTOS;
    }

    public void setAuthorDTOS(List<AuthorDTO> authorDTOS) {
        this.authorDTOS = authorDTOS;
    }
}
