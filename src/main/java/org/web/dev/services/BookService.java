package org.web.dev.services;

import org.example.dtos.books.CreateBookForm;
import org.example.dtos.books.UpdateBookForm;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.GenreDTO;

import java.util.List;

public interface BookService {
    void createBook(CreateBookForm createBookForm);
    Page<BookDTO> getPage(int page, int size);
    List<BookDTO> getPopularByGenre(Long id);
    BookDTO findById(Long id);
    Page<BookDTO> getPageByGenre(Long id, int page, int size);
    List<BookDTO> findAll();
    void update(UpdateBookForm form);
    void delete(Long id);
    Page<BookDTO> search(List<Long> selectedGenres, List<Long> selectedAuthors, String title, int page, int size);
}
