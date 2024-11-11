package org.web.dev.runner;

import org.springframework.boot.CommandLineRunner;
import org.web.dev.repositories.*;

public class ReposTester implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ReposTester(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            authorRepository.findAll();
            bookRepository.findAll();
            genreRepository.findAll();
            orderRepository.findAll();
            userRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error while testing repositories: " + e.getMessage());
        }
    }
}
