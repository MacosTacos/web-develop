package org.web.dev.services.impl;

import org.modelmapper.ModelMapper;
import org.web.dev.domain.entities.GenreEntity;
import org.web.dev.dtos.GenreDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.GenreRepository;

import java.util.List;

public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreService(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    public void createGenre(GenreDTO genreDTO) {
        if (genreDTO != null && genreDTO.getName() != null) {
            GenreEntity genreEntity = new GenreEntity(genreDTO.getName());
            genreRepository.save(genreEntity);
        }
    }

    public GenreDTO getGenre(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("genre with id " + id + " not found"));
        GenreDTO genreDTO = modelMapper.map(genreEntity, GenreDTO.class);
        return genreDTO;
    }

    public List<GenreDTO> getAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreDTO> genreDTOs = genreEntities.stream().map(
            genreEntity -> modelMapper.map(genreEntity, GenreDTO.class)
        ).toList();
        return genreDTOs;
    }


}
