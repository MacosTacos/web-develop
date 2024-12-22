package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.dev.domain.entities.GenreEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends BaseRepository<GenreEntity, Long> {

    @Query(value = "select g from GenreEntity g where g.id in :ids and g.deleted = false")
    List<GenreEntity> findAllById(@Param(value = "ids") List<Long> ids);

    @Query(value = "select g from GenreEntity g where g.deleted = false")
    List<GenreEntity> findAll();

    @Query(value = "select g from GenreEntity g where g.id = :id and g.deleted = false")
    Optional<GenreEntity> findById(@Param("id") Long id);
}
