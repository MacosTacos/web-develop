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
    public void createBook(BookDTO bookDTO) {
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
        bookRepository.save(bookEntity);
    }

    @Override
    public BookDTO findById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found"));
        BookDTO bookDTO = modelMapper.map(bookEntity, BookDTO.class);
        return bookDTO;
    }

    @Override
    public List<BookDTO> findAll() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        List<BookDTO> bookDTOS = bookEntities.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .toList();
        return bookDTOS;
    }

    @Override
    public void delete(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found"));
        bookEntity.setDeleted(true);
        bookRepository.save(bookEntity);
    }

}
