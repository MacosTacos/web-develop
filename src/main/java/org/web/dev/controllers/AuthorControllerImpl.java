package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.AuthorController;
import org.example.dtos.authors.AuthorView;
import org.example.dtos.authors.AuthorsView;
import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.services.AuthorService;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final ModelMapper modelMapper;

    public AuthorControllerImpl(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
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
        AuthorDTO authorDTO = modelMapper.map(form, AuthorDTO.class);
        authorService.createAuthor(authorDTO);
        return "redirect:/authors";
    }

    @Override
    public String getAll(Principal principal, Model model) {
        LOG.info("GET:/authors Show all authors for " + principal.getName());
        List<AuthorDTO> authorDTOS = authorService.getAll();
        List<AuthorView> authorViews = authorDTOS.stream().map(
                authorDTO -> modelMapper.map(authorDTO, AuthorView.class)
        ).toList();
        AuthorsView authorsView = new AuthorsView(authorViews);
        model.addAttribute("authors", authorsView);
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
        AuthorDTO authorDTO = modelMapper.map(form, AuthorDTO.class);
        if (bindingResult.hasErrors()) {
            return "authors/author-update-page.html";
        }
        authorService.update(authorDTO);
        return "redirect:/authors";
    }

    @Override
    public String deleteAuthor(Long id, Principal principal, Model model) {
        LOG.info("GET:/authors/delete Author delete request from " + principal.getName());
        authorService.delete(id);
        return "redirect:/authors";
    }
}
