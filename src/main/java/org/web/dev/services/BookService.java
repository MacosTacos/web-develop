package org.web.dev.services;

import org.springframework.http.ResponseEntity;
import org.web.dev.dtos.BookDTO;

public interface BookService {
    ResponseEntity<BookDTO> createBook(BookDTO bookDTO);
    ResponseEntity<BookDTO> findById(Long id);
    void delete(Long id);
}
