package org.web.dev.config;

import org.example.dtos.books.CreateBookForm;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.domain.entities.BookEntity;
import org.web.dev.dtos.AuthorDTO;
import org.web.dev.dtos.BookDTO;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(BookEntity.class, BookDTO.class)
                .addMappings(mapper -> mapper.map(BookEntity::getGenreEntities, BookDTO::setGenreDTOS))
                .addMappings(mapper -> mapper.map(BookEntity::getAuthorEntities, BookDTO::setAuthorDTOS));

        return modelMapper;
    }
}
