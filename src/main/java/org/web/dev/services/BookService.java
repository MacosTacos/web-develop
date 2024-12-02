package org.web.dev.services;

import org.springframework.http.ResponseEntity;
import org.web.dev.dtos.BookDTO;

import java.util.List;

public interface BookService {
    void createBook(BookDTO bookDTO);
    BookDTO findById(Long id);
    List<BookDTO> findAll();
    void delete(Long id);
}
