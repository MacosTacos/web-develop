package org.web.dev.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);
    Iterable<T> findAll();
    Optional<T> findById(ID id);
}
