package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.dev.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends BaseRepository<BookEntity, Long> {

    @Query(value = "select b from BookEntity b where b.deleted = false")
    List<BookEntity> findAll();


    @Query(value = "select b from BookEntity b where b.deleted = false and b.id = :id")
    Optional<BookEntity> findById(@RequestParam(value = "id") Long id);
}
