package org.web.dev.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.GenresController;
import org.example.dtos.genres.CreateGenreForm;
import org.example.dtos.genres.UpdateGenreForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.services.GenreService;
import org.web.dev.services.impl.GenreServiceImpl;

import java.security.Principal;

@Controller
public class GenreController implements GenresController {
    private final GenreService genreService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @Override
    public String create(Principal principal, Model model) {
        LOG.info("GET:/genres/create Genre create page request from " + principal.getName());
        model.addAttribute("createGenreForm", new CreateGenreForm(""));
        return "genres/genre-create-page.html";
    }

    @Override
    public String create(CreateGenreForm createGenreForm, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("GET:/genres/create New genre create request from " + principal.getName());
        genreService.createGenre(createGenreForm);
        return "redirect:/genres";
    }

    @Override
    public String getAll(Principal principal, Model model) {
        LOG.info("GET:/genres All genres request from " + (principal != null ? principal.getName() : "not_authenticated"));
        model.addAttribute("genres", genreService.getAll());
        return "genres/genres.html";
    }

    @Override
    public String update(Long id, Principal principal, Model model) {
        LOG.info("GET:/genres/update/{} Genre update page request from {}", id, principal.getName());
        model.addAttribute("genre", genreService.getGenre(id));
        return "genres/genre-update-page.html";
    }

    @Override
    public String update(UpdateGenreForm updateGenreForm, BindingResult bindingResult, Principal principal, Model model) {
        LOG.info("POST:/genres/update/{} Genre update request from {}", updateGenreForm.id(), principal.getName());
        genreService.update(updateGenreForm);
        return "redirect:/genres";
    }

    @Override
    public String delete(Long id, Principal principal) {
        LOG.info("GET:/genres/delete Genre delete request from " + principal.getName());
        genreService.delete(id);
        return "redirect:/genres";
    }
}
