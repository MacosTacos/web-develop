package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.web.dev.domain.entities.AuthorEntity;
import org.web.dev.domain.entities.GenreEntity;
import org.web.dev.domain.entities.OrderContentEntity;

import java.util.List;

public interface OrderContentRepository extends BaseRepository<OrderContentEntity, Long> {

    @Query(value = "select oc.bookEntity, sum(oc.quantity) as quantity from OrderContentEntity oc " +
            "join oc.bookEntity b join b.genreEntities g where g in :genreEntities group by oc.bookEntity order by quantity desc")
    List<Object[]> findPopularByGenre(@Param("genreEntities") List<GenreEntity> genreEntities);






}
