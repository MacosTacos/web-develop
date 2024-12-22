package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.dev.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends BaseRepository<AuthorEntity, Long> {

    @Query(value = "select a from AuthorEntity a where a.id in :ids and a.deleted = false")
    List<AuthorEntity> findAllById(@Param(value = "ids") List<Long> ids);

    @Query(value = "select a from AuthorEntity a where a.deleted = false")
    List<AuthorEntity> findAll();

    @Query(value = "select a from AuthorEntity a where a.id = :id and a.deleted = false")
    Optional<AuthorEntity> findById(@Param("id") Long id);
}
