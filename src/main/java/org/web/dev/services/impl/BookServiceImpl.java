package org.web.dev.services.impl;

import org.example.dtos.books.CreateBookForm;
import org.example.dtos.books.UpdateBookForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.domain.entities.BookEntity;
import org.web.dev.domain.entities.GenreEntity;
import org.web.dev.dtos.BookDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.AuthorRepository;
import org.web.dev.repositories.BookRepository;
import org.web.dev.repositories.GenreRepository;
import org.web.dev.repositories.OrderContentRepository;
import org.web.dev.services.BookService;

import java.util.List;

@Service
//@EnableCaching
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final OrderContentRepository orderContentRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, GenreRepository genreRepository, AuthorRepository authorRepository, OrderContentRepository orderContentRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.orderContentRepository = orderContentRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "page_by_genre", allEntries = true),
            @CacheEvict(value = "books_search", allEntries = true),
            @CacheEvict(value = "genres", allEntries = true)
    })
    public void createBook(BookDTO bookDTO, List<Long> genreIds, List<Long> authorIds) {
        List<GenreEntity> genreEntities = genreRepository.findAllById(genreIds);
        List<AuthorEntity> authorEntities = authorRepository.findAllById(authorIds);
        BookEntity bookEntity = new BookEntity(
                bookDTO.getName(),
                bookDTO.getPublicationYear(),
                bookDTO.getPrice(),
                genreEntities,
                authorEntities
        );
        bookRepository.save(bookEntity);
    }

    @Override
    public List<BookDTO> getPopularByGenre(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + id + " not found"));
        List<Object[]> popularBooks = orderContentRepository.findPopularByGenre(List.of(genreEntity));
        List<BookEntity> popularBookEntities = popularBooks.stream()
                .map(elem -> (BookEntity) elem[0])
                .limit(5).toList();
        List<BookDTO> bookDTOS = popularBookEntities.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).toList();
        return bookDTOS;
    }

    @Override
    @Cacheable(value = "page_by_genre")
    public Page<BookDTO> getPageByGenre(Long id, int page, int size) {
        Page<BookEntity> bookEntityPage;
        Pageable pageable = PageRequest.of(page, size);
        if (id == null) {
            bookEntityPage = bookRepository.getPage(pageable);
        } else {
            GenreEntity genreEntity = genreRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + id + " not found"));
            bookEntityPage = bookRepository.getPageByGenre(List.of(genreEntity), pageable);
        }
        Page<BookDTO> bookDTOPage = bookEntityPage.map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));
        return bookDTOPage;
    }

    @Override
    @Cacheable(value = "book")
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
    @Cacheable(value = "books_search")
    public Page<BookDTO> search(List<Long> selectedGenres, List<Long> selectedAuthors, String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<AuthorEntity> authorEntities = authorRepository.findAllById(selectedAuthors);
        List<GenreEntity> genreEntities = genreRepository.findAllById(selectedGenres);
        Page<BookEntity> bookEntities;
        Page<BookDTO> bookDTOS;
        if (title != null && !title.isEmpty()) {
            if (authorEntities != null && !authorEntities.isEmpty() && genreEntities != null && !genreEntities.isEmpty()) {
                bookEntities = bookRepository.getByTitleAndGenresAndAuthors(title, genreEntities, authorEntities, pageable);
            } else if (authorEntities != null && !authorEntities.isEmpty()) {
                bookEntities = bookRepository.getByTitleAndAuthors(title, authorEntities, pageable);
            } else if (genreEntities != null && !genreEntities.isEmpty()) {
                bookEntities = bookRepository.getByTitleAndGenres(title, genreEntities, pageable);
            } else {
                bookEntities = bookRepository.getByTitle(title, pageable);
            }
        } else {
            if (authorEntities != null && !authorEntities.isEmpty() && genreEntities != null && !genreEntities.isEmpty()) {
                bookEntities = bookRepository.getByAuthorsAndGenres(authorEntities, genreEntities, pageable);
            } else if (authorEntities != null && !authorEntities.isEmpty()) {
                bookEntities = bookRepository.getPageByAuthor(authorEntities, pageable);
            } else if (genreEntities != null && !genreEntities.isEmpty()) {
                bookEntities = bookRepository.getPageByGenre(genreEntities, pageable);
            } else {
                bookEntities = bookRepository.getPage(pageable);
            }
        }
        bookDTOS = bookEntities.map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));
        return bookDTOS;
    }

    @Override
    public Page<BookDTO> getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> bookEntityPage = bookRepository.getPage(pageable);
        Page<BookDTO> bookDTOPage = bookEntityPage.map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));
        return bookDTOPage;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "book", key = "#bookDTO.getId()"),
            @CacheEvict(value = "page_by_genre", allEntries = true),
            @CacheEvict(value = "books_search", allEntries = true)
    })
    public void update(BookDTO bookDTO, List<Long> genreIds, List<Long> authorIds) {
        BookEntity bookEntity = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookDTO.getId() + "not found"));
        bookEntity.setName(bookDTO.getName());
        bookEntity.setPublicationYear(bookDTO.getPublicationYear());
        bookEntity.setPrice(bookDTO.getPrice());
        bookEntity.setDescription(bookDTO.getDescription());
        bookEntity.setGenreEntities(genreRepository.findAllById(genreIds));
        bookEntity.setAuthorEntities(authorRepository.findAllById(authorIds));
        bookRepository.save(bookEntity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "book", key = "#id"),
            @CacheEvict(value = "page_by_genre", allEntries = true),
            @CacheEvict(value = "books_search", allEntries = true)
    })
    public void delete(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found"));
        bookEntity.setDeleted(true);
        bookRepository.save(bookEntity);
    }

}
