package org.web.dev.controllers;

import org.example.controllers.BookController;
import org.example.dtos.books.CreateBookForm;
import org.example.dtos.books.UpdateBookForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.dtos.BookDTO;
import org.web.dev.dtos.GenreDTO;
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
        model.addAttribute("adventures", bookService.getPopularByGenre(1L));
        model.addAttribute("detectives", bookService.getPopularByGenre(2L));
        model.addAttribute("classics", bookService.getPopularByGenre(8L));
        return "main/home-page.html";
    }


    @Override
    public String getAll(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/books.html";
    }

    @Override
    public String search(List<Long> genreIds,
                         List<Long> authorIds,
                         String title,
                         int page,
                         int size,
                         Model model) {
        model.addAttribute("results", bookService.search(genreIds, authorIds, title, page, size));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("selectedGenres", genreIds != null ? genreIds : new ArrayList<Long>());
        model.addAttribute("selectedAuthors", authorIds != null ? authorIds : new ArrayList<Long>());
        model.addAttribute("title", title);
        return "main/search-page.html";
    }

    @Override
    public String createBookForm(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("form", new CreateBookForm(null, null, null, null, null, null));
        return "books/book-create-page.html";
    }

    @Override
    public String createBook(CreateBookForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "books/book-create-page.html";
        }
        bookService.createBook(form);
        return "redirect:/books/list";
    }

    @Override
    public String updateBookForm(Long id, Model model) {
        BookDTO bookDTO = bookService.findById(id);
        List<Long> genreIds = bookDTO.getGenreDTOS().stream()
                        .map(GenreDTO::getId).toList();
        System.out.println(genreIds.getFirst());
        List<Long> authorIds = bookDTO.getAuthorDTOS().stream()
                        .map(AuthorDTO::getId).toList();
        UpdateBookForm form = new UpdateBookForm(bookDTO.getId(), bookDTO.getName(), bookDTO.getPrice(), bookDTO.getPublicationYear(), bookDTO.getDescription(), genreIds, authorIds);
        model.addAttribute("form", form);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "books/book-update-page.html";
    }

    @Override
    public String updateBook(UpdateBookForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "books/book-update-page.html";
        }
        bookService.update(form);
        return "redirect:/books/list";
    }

    @Override
    public String deleteBook(Long id, Model model) {
        bookService.delete(id);
        return "redirect:/books/list";
    }
}
