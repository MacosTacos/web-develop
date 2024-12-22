package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.AuthorController;
import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.services.AuthorService;

import java.security.Principal;

@Controller
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public String createAuthorForm(Principal principal, Model model) {
        LOG.info("GET:/authors/create  Opened create author form page for " + principal.getName());
        model.addAttribute("form", new CreateAuthorForm(null, null));
        return "authors/author-create-page.html";
    }

    @Override
    public String createAuthor(CreateAuthorForm form, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("POST:/authors/create New author create request from " + principal.getName());
        if (bindingResult.hasErrors()) {
            return "authors/author-create-page.html";
        }
        authorService.createAuthor(form);
        return "redirect:/authors";
    }

    @Override
    public String getAll(Principal principal, Model model) {
        LOG.info("GET:/authors Show all authors for " + principal.getName());
        model.addAttribute("authors", authorService.getAll());
        return "authors/authors.html";
    }

    @Override
    public String updateAuthorForm(Long id, Principal principal, Model model) {
        LOG.info("GET:/authors/update  Opened update author form page for " + principal.getName());
        AuthorDTO authorDTO = authorService.getAuthor(id);
        model.addAttribute("form", new UpdateAuthorForm(authorDTO.getId(), authorDTO.getName(), authorDTO.getDescription()));
        return "authors/author-update-page.html";
    }

    @Override
    public String updateAuthor(UpdateAuthorForm form, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("POST:/authors/update Author update request from " + principal.getName());
        if (bindingResult.hasErrors()) {
            return "authors/author-update-page.html";
        }
        authorService.update(form);
        return "redirect:/authors";
    }

    @Override
    public String deleteAuthor(Long id, Principal principal, Model model) {
        LOG.info("GET:/authors/delete Author delete request from " + principal.getName());
        authorService.delete(id);
        return "redirect:/authors";
    }
}
