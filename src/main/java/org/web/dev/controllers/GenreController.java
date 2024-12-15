package org.web.dev.controllers;


import org.example.controllers.GenresController;
import org.example.dtos.genres.CreateGenreForm;
import org.example.dtos.genres.UpdateGenreForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.web.dev.services.GenreService;
import org.web.dev.services.impl.GenreServiceImpl;

@Controller
public class GenreController implements GenresController {
    private final GenreService genreService;

    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @Override
    public String create(Model model) {
        model.addAttribute("createGenreForm", new CreateGenreForm(""));
        return "genres/genre-create-page.html";
    }

    @Override
    public String create(CreateGenreForm createGenreForm, BindingResult bindingResult, Model model) {
        genreService.createGenre(createGenreForm);
        return "redirect:/genres";
    }

    @Override
    public String getAll(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genres/genres.html";
    }

    @Override
    public String update(Long id, Model model) {
        model.addAttribute("genre", genreService.getGenre(id));
        return "genres/genre-update-page.html";
    }

    @Override
    public String update(UpdateGenreForm updateGenreForm, BindingResult bindingResult, Model model) {
        genreService.update(updateGenreForm);
        return "redirect:/genres";
    }
}
