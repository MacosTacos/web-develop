package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.dev.domain.entities.GenreEntity;

import java.util.List;

public interface GenreRepository extends BaseRepository<GenreEntity, Long> {

    @Query(value = "select g from GenreEntity g where g.id in :ids")
    List<GenreEntity> findAllById(@RequestParam(value = "ids") List<Long> ids);
}
