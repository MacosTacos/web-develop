package org.web.dev.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.domain.entities.BookEntity;
import org.web.dev.domain.entities.GenreEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends BaseRepository<BookEntity, Long> {

    @Query(value = "select b from BookEntity b where b.deleted = false")
    List<BookEntity> findAll();


    @Query(value = "select b from BookEntity b where b.id = :id and b.deleted = false")
    Optional<BookEntity> findById(@Param(value = "id") Long id);

    @Query(value = "select b from BookEntity b where b.deleted = false")
    Page<BookEntity> getPage(Pageable pageable);

    @Query(value = "select b from BookEntity b join b.genreEntities g where g in :genreEntities and b.deleted = false")
    Page<BookEntity> getPageByGenre(@Param("genreEntities") List<GenreEntity> genreEntities, Pageable pageable);

    @Query(value = "select b from BookEntity b join b.authorEntities a where a in :authorEntities and b.deleted = false")
    Page<BookEntity> getPageByAuthor(@Param("authorEntities") List<AuthorEntity> authorEntities, Pageable pageable);

    @Query(value = "select b from BookEntity b where lower(b.name) like lower(concat('%', :title, '%')) and b.deleted = false")
    Page<BookEntity> getByTitle(@Param("title") String title, Pageable pageable);

    @Query(value = "select b from BookEntity b join b.authorEntities a where a in :authors and lower(b.name) like lower(concat('%', :title, '%')) and b.deleted = false")
    Page<BookEntity> getByTitleAndAuthors(@Param("title") String title, @Param("authors") List<AuthorEntity> authors, Pageable pageable);

    @Query(value = "select b from BookEntity b join b.genreEntities g where g in :genres and lower(b.name) like lower(concat('%', :title, '%')) and b.deleted = false")
    Page<BookEntity> getByTitleAndGenres(@Param("title") String title, @Param("genres") List<GenreEntity> genres, Pageable pageable);

    @Query(value = "select b from BookEntity b join b.genreEntities g join b.authorEntities a where g in :genres and a in :authors and lower(b.name) like lower(concat('%', :title, '%')) and b.deleted = false")
    Page<BookEntity> getByTitleAndGenresAndAuthors(@Param("title") String title, @Param("genres") List<GenreEntity> genres, @Param("authors") List<AuthorEntity> authors, Pageable pageable);

    @Query(value = "select b from BookEntity b join b.genreEntities g join b.authorEntities a where g in :genres and a in :authors and b.deleted = false")
    Page<BookEntity> getByAuthorsAndGenres(@Param("authors") List<AuthorEntity> authors, @Param("genres") List<GenreEntity> genres, Pageable pageable);

}
