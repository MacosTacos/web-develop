package org.web.dev.services.impl;

import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.exceptions.ResourceNotFoundException;
import org.web.dev.repositories.AuthorRepository;
import org.web.dev.services.AuthorService;

import java.util.List;

@Service
@EnableCaching
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = "authors", allEntries = true)
    public void createAuthor(CreateAuthorForm createAuthorForm) {
        if (createAuthorForm != null && createAuthorForm.name() != null) {
            AuthorEntity authorEntity = new AuthorEntity(createAuthorForm.name(), createAuthorForm.description());
            authorRepository.save(authorEntity);
        }
    }

    @Override
    @Cacheable(value = "authors")
    public List<AuthorDTO> getAll() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        List<AuthorDTO> authorDTOS = authorEntities.stream().map(
                authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class)
        ).toList();
        return authorDTOS;
    }

    @Override
    @Cacheable(value = "author")
    public AuthorDTO getAuthor(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + id + " not found"));
        AuthorDTO authorDTO = modelMapper.map(authorEntity, AuthorDTO.class);
        return authorDTO;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "authors", allEntries = true),
            @CacheEvict(value = "author", key = "#updateAuthorForm.id()")
    })
    public void update(UpdateAuthorForm updateAuthorForm) {
        AuthorEntity authorEntity = authorRepository.findById(updateAuthorForm.id())
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + updateAuthorForm.id() + " not found"));
        authorEntity.setName(updateAuthorForm.name());
        authorEntity.setDescription(updateAuthorForm.description());
        authorRepository.save(authorEntity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "authors", allEntries = true),
            @CacheEvict(value = "author", key = "#id")
    })
    public void delete(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        authorEntity.setDeleted(true);
        authorRepository.save(authorEntity);
    }



}
