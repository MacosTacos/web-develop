package org.web.dev.services;

import org.web.dev.dtos.AuthorDTO;

import java.util.List;

public interface AuthorService {
    void createAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> getAll();
    AuthorDTO getAuthor(Long id);
    void update(AuthorDTO authorDTO);
    void delete(Long id);
}
