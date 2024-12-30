package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.BookController;
import org.example.dtos.authors.AuthorView;
import org.example.dtos.books.*;
import org.example.dtos.genres.GenreView;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.GenreDTO;
import org.web.dev.services.AuthorService;
import org.web.dev.services.BookService;
import org.web.dev.services.GenreService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final ModelMapper modelMapper;

    public BookControllerImpl(BookService bookService, GenreService genreService, AuthorService authorService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String singleBookPage(Long id, Principal principal, Model model) {
        LOG.info("GET:/books/{} Single book page request from {}", id, principal != null ? principal.getName() : "not_authenticated");
        model.addAttribute("book", bookService.findById(id));
        return "main/single-book-page.html";
    }

    @Override
    public String booksByGenre(Long id, Principal principal, int page, int size, Model model) {
        LOG.info("GET:/books/genre/{} Books by genre request from {}", id, principal != null ? principal.getName() : "not_authenticated");
        Page<BookView> bookViews = bookService.getPageByGenre(id, page, size).map(bookDTO -> modelMapper.map(bookDTO, BookView.class));
        List<GenreView> genreViews = genreService.getAll().stream().map(
                genreDTO -> modelMapper.map(genreDTO, GenreView.class)
        ).toList();
        BookByGenreView bookByGenreView = new BookByGenreView(bookViews, genreViews);
        model.addAttribute("view", bookByGenreView);
        return "main/books-by-genre-page.html";
    }

    @Override
    public String mainPage(Principal principal, Model model) {
        LOG.info("GET:/books/main Main page request from " + (principal != null ? principal.getName() : "not_authenticated"));
        List<BookView> booksByFirstGenre = bookService.getPopularByGenre(4L).stream().map(
                bookDTO -> modelMapper.map(bookDTO, BookView.class)
        ).toList();
        List<BookView> booksBySecondGenre = bookService.getPopularByGenre(1L).stream().map(
                bookDTO -> modelMapper.map(bookDTO, BookView.class)
        ).toList();
        List<BookView> booksByThirdGenre = bookService.getPopularByGenre(5L).stream().map(
                bookDTO -> modelMapper.map(bookDTO, BookView.class)
        ).toList();
        MainPageView mainPageView = new MainPageView(booksByFirstGenre, booksBySecondGenre, booksByThirdGenre);
        model.addAttribute("view", mainPageView);
        return "main/home-page.html";
    }


    @Override
    public String getAll(Principal principal, Model model) {
        LOG.info("GET:/books/list All books request from " + principal.getName());
        List<BookView> bookViews = bookService.findAll().stream().map(
                bookDTO -> modelMapper.map(bookDTO, BookView.class)
        ).toList();
        model.addAttribute("books", bookViews);
        return "books/books.html";
    }

    @Override
    public String search(List<Long> genreIds,
                         List<Long> authorIds,
                         String title,
                         int page,
                         int size,
                         Principal principal,
                         Model model) {
        LOG.info("GET:/books/search Search request from " + (principal != null ? principal.getName() : "not_authenticated"));
        Page<BookView> results = bookService.search(genreIds, authorIds, title, page, size).map(bookDTO -> modelMapper.map(bookDTO, BookView.class));
        List<GenreView> genreViews = genreService.getAll().stream().map(
                genreDTO -> modelMapper.map(genreDTO, GenreView.class)
        ).toList();
        List<AuthorView> authorViews = authorService.getAll().stream().map(
                authorDTO -> modelMapper.map(authorDTO, AuthorView.class)
        ).toList();
        SearchView searchView = new SearchView(results, genreViews, authorViews);
        model.addAttribute("view", searchView);
        model.addAttribute("selectedGenres", genreIds != null ? genreIds : new ArrayList<Long>());
        model.addAttribute("selectedAuthors", authorIds != null ? authorIds : new ArrayList<Long>());
        model.addAttribute("title", title);
        return "main/search-page.html";
    }

    @Override
    public String createBookForm(Principal principal, Model model) {
        LOG.info("GET:/books/create Book create page request from " + principal.getName());
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("form", new CreateBookForm());
        return "books/book-create-page.html";
    }

    @Override
    public String createBook(CreateBookForm form, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("POST:/books/create New book create request from " + principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "books/book-create-page.html";
        }
        BookDTO bookDTO = modelMapper.map(form, BookDTO.class);
        bookService.createBook(bookDTO, form.getGenreIds(), form.getAuthorIds());
        return "redirect:/books/list";
    }

    @Override
    public String updateBookForm(Long id, Principal principal, Model model) {
        LOG.info("GET:/books/update/{} Book update page request from {}", id, principal.getName());
        BookDTO bookDTO = bookService.findById(id);
        List<Long> genreIds = bookDTO.getGenreDTOS().stream()
                        .map(GenreDTO::getId).toList();
        System.out.println(genreIds.getFirst());
        List<Long> authorIds = bookDTO.getAuthorDTOS().stream()
                        .map(AuthorDTO::getId).toList();
        UpdateBookForm form = new UpdateBookForm(bookDTO.getId(), bookDTO.getName(), bookDTO.getDescription(), bookDTO.getPrice(), bookDTO.getPublicationYear(), genreIds, authorIds);
        model.addAttribute("form", form);
        List<GenreView> genreViews = genreService.getAll().stream().map(
                genreDTO -> modelMapper.map(genreDTO, GenreView.class)
        ).toList();
        model.addAttribute("genres", genreViews);
        List<AuthorView> authorViews = authorService.getAll().stream().map(
                authorDTO -> modelMapper.map(authorDTO, AuthorView.class)
        ).toList();
        model.addAttribute("authors", authorViews);
        return "books/book-update-page.html";
    }

    @Override
    public String updateBook(UpdateBookForm form, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("POST:/books/update{} Book update request from {}", form.getId(), principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "books/book-update-page.html";
        }
        BookDTO bookDTO = modelMapper.map(form, BookDTO.class);
        bookService.update(bookDTO, form.getGenreIds(), form.getAuthorIds());
        return "redirect:/books/list";
    }

    @Override
    public String deleteBook(Long id, Principal principal, Model model) {
        LOG.info("GET:/books/delete/{} Book delete request from {}", id, principal.getName());
        bookService.delete(id);
        return "redirect:/books/list";
    }
}
