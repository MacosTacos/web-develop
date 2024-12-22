package org.web.dev.services;

import org.example.dtos.genres.CreateGenreForm;
import org.example.dtos.genres.UpdateGenreForm;
import org.web.dev.dtos.GenreDTO;

import java.util.List;

public interface GenreService {
    void createGenre(CreateGenreForm createGenreForm);
    GenreDTO getGenre(Long id);
    List<GenreDTO> getAll();
    void update(UpdateGenreForm updateGenreForm);
    void delete(Long id);
}
