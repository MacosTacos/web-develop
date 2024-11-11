package org.web.dev.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.BookEntity;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.GenreDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.AuthorRepository;
import org.web.dev.repositories.BookRepository;
import org.web.dev.repositories.GenreRepository;
import org.web.dev.services.BookService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public ResponseEntity<BookDTO> createBook(BookDTO bookDTO) {
        BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);
        List<Long> ids = new ArrayList<>();
        for (GenreDTO genreDTO : bookDTO.getGenreDTOS()) {
            ids.add(genreDTO.getId());
        }
        bookEntity.setGenreEntities(genreRepository.findAllById(ids));
        ids.clear();
        for (AuthorDTO authorDTO : bookDTO.getAuthorDTOS()) {
            ids.add(authorDTO.getId());
        }
        bookEntity.setAuthorEntities(authorRepository.findAllById(ids));
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        BookDTO savedBookDTO = modelMapper.map(savedBookEntity, BookDTO.class);
        ResponseEntity<BookDTO> responseEntity = ResponseEntity.ok().body(savedBookDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<BookDTO> findById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found"));
        BookDTO bookDTO = modelMapper.map(bookEntity, BookDTO.class);
        ResponseEntity<BookDTO> responseEntity = ResponseEntity.ok().body(bookDTO);
        return responseEntity;
    }

    @Override
    public void delete(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found"));
        bookEntity.setDeleted(true);
        bookRepository.save(bookEntity);
    }

}
