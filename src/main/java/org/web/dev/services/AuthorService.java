package org.web.dev.services;

import org.example.dtos.authors.CreateAuthorForm;
import org.example.dtos.authors.UpdateAuthorForm;
import org.web.dev.dtos.AuthorDTO;

import java.util.List;

public interface AuthorService {
    void createAuthor(CreateAuthorForm createAuthorForm);
    List<AuthorDTO> getAll();
    AuthorDTO getAuthor(Long id);
    void update(UpdateAuthorForm updateAuthorForm);
    void delete(Long id);
}
