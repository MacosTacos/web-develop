package org.web.dev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.web.dev.domain.enums.OrderStatus;
import org.web.dev.domain.entities.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Long> {

    @Query("select o from OrderEntity o where o.status = :orderStatus")
    List<OrderEntity> findByStatus(@Param(value = "orderStatus") OrderStatus orderStatus);
}
