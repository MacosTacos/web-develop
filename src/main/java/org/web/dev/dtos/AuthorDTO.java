package org.web.dev.dtos;


import java.io.Serializable;
import java.util.List;

public class AuthorDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private List<BookDTO> bookDTOS;

    public AuthorDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BookDTO> getBookEntities() {
        return bookDTOS;
    }

    public void setBookEntities(List<BookDTO> bookEntities) {
        this.bookDTOS = bookEntities;
    }
}
