package org.web.dev.services.impl;

import org.example.dtos.genres.CreateGenreForm;
import org.example.dtos.genres.UpdateGenreForm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.GenreEntity;
import org.web.dev.dtos.GenreDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.GenreRepository;
import org.web.dev.services.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createGenre(CreateGenreForm createGenreForm) {
        if (createGenreForm != null && createGenreForm.name() != null) {
            GenreEntity genreEntity = new GenreEntity(createGenreForm.name());
            genreRepository.save(genreEntity);
        }
    }

    @Override
    public GenreDTO getGenre(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("genre with id " + id + " not found"));
        GenreDTO genreDTO = modelMapper.map(genreEntity, GenreDTO.class);
        return genreDTO;
    }

    @Override
    public List<GenreDTO> getAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreDTO> genreDTOs = genreEntities.stream().map(
            genreEntity -> modelMapper.map(genreEntity, GenreDTO.class)
        ).toList();
        return genreDTOs;
    }

    @Override
    public void update(UpdateGenreForm updateGenreForm) {
        GenreEntity genreEntity = genreRepository.findById(updateGenreForm.id())
                .orElseThrow(() -> new ResourceNotFoundException("genre with id " + updateGenreForm.id() + " not found"));
        genreEntity.setName(updateGenreForm.name());
        genreRepository.save(genreEntity);
    }

}
