package org.web.dev.services.impl;

import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.AuthorRepository;
import org.web.dev.services.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAuthor(CreateAuthorForm createAuthorForm) {
        if (createAuthorForm != null && createAuthorForm.name() != null) {
            AuthorEntity authorEntity = new AuthorEntity(createAuthorForm.name(), createAuthorForm.description());
            authorRepository.save(authorEntity);
        }
    }

    @Override
    public List<AuthorDTO> getAll() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        List<AuthorDTO> authorDTOS = authorEntities.stream().map(
                authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class)
        ).toList();
        return authorDTOS;
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + id + " not found"));
        AuthorDTO authorDTO = modelMapper.map(authorEntity, AuthorDTO.class);
        return authorDTO;
    }

    @Override
    public void update(UpdateAuthorForm updateAuthorForm) {
        AuthorEntity authorEntity = authorRepository.findById(updateAuthorForm.id())
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + updateAuthorForm.id() + " not found"));
        authorEntity.setName(updateAuthorForm.name());
        authorEntity.setDescription(updateAuthorForm.description());
        authorRepository.save(authorEntity);
    }



}
