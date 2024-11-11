package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.dev.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorRepository extends BaseRepository<AuthorEntity, Long> {

    @Query(value = "select a from AuthorEntity a where a.id in :ids")
    List<AuthorEntity> findAllById(@RequestParam(value = "ids") List<Long> ids);
}
