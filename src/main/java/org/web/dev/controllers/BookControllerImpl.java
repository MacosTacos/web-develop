package org.web.dev.controllers;

import org.example.controllers.BookController;
import org.example.dtos.books.CreateBookForm;
import org.example.dtos.SearchForm;
import org.example.dtos.books.UpdateBookForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.dev.dtos.BookDTO;
import org.web.dev.services.AuthorService;
import org.web.dev.services.BookService;
import org.web.dev.services.GenreService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookControllerImpl(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Override
    public String singleBookPage(Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "main/single-book-page.html";
    }

    @Override
    public String booksByGenre(Long id, int page, int size, Model model) {
        model.addAttribute("books", bookService.getPageByGenre(id, page, size));
        model.addAttribute("genres", genreService.getAll());
        return "main/books-by-genre-page.html";
    }

    @Override
    public String mainPage(Model model) {
        model.addAttribute("adventures", bookService.findAll());
        model.addAttribute("detectives", bookService.findAll());
        model.addAttribute("classics", bookService.findAll());
        return "main/home-page.html";
    }

    //    @Override
//    public String mainPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            Model model) {
//        model.addAttribute("books", bookService.getPage(page, size));
//        return "books/books.html";
//    }

    @Override
    public String getAll(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/books.html";
    }

    @Override
    public String search(List<Long> selectedGenres,
                         List<Long> selectedAuthors,
                         String title,
                         int page,
                         int size,
                         Model model) {
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("selectedGenres", selectedAuthors != null ? selectedAuthors : new ArrayList<Long>());
        model.addAttribute("selectedAuthors", selectedGenres != null ? selectedGenres : new ArrayList<Long>());
        model.addAttribute("results", bookService.search(selectedGenres, selectedAuthors, title, page, size));
        model.addAttribute("title", title);
        return "main/search-page.html";
    }

    @Override
    public String createBookForm(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "books/book-create-page.html";
    }

    @Override
    public String createBook(CreateBookForm form, BindingResult bindingResult, Model model) {
        bookService.createBook(form);
        return "redirect:/books";
    }

    @Override
    public String updateBookForm(Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "books/book-update-page.html";
    }

    @Override
    public String updateBook(UpdateBookForm form, BindingResult bindingResult, Model model) {
        bookService.update(form);
        return "redirect:/books";
    }

    @Override
    public String deleteBook(Long id, Model model) {
        return "";
    }
}
