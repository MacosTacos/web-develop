package org.web.dev.controllers;

import org.example.controllers.AuthorController;
import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.services.AuthorService;

@Controller
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public String createAuthorForm(Model model) {
        return "authors/author-create-page.html";
    }

    @Override
    public String createAuthor(CreateAuthorForm form, BindingResult bindingResult, Model model) {
        authorService.createAuthor(form);
        return "redirect:/authors";
    }

    @Override
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authors/authors.html";
    }

    @Override
    public String updateAuthorForm(Long id, Model model) {
        model.addAttribute("author", authorService.getAuthor(id));
        return "authors/author-update-page.html";
    }

    @Override
    public String updateAuthor(UpdateAuthorForm form, BindingResult bindingResult, Model model) {
        authorService.update(form);
        return "redirect:/authors";
    }

    @Override
    public String deleteAuthor(Long id, Model model) {
        return "";
    }
}
