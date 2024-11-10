package org.web.dev.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "order_contents")
public class OrderContentEntity extends BaseEntity {
    private OrderEntity orderEntity;
    private BookEntity bookEntity;
    private Integer quantity;

    protected OrderContentEntity() {
    }

    public OrderContentEntity(OrderEntity orderEntity, BookEntity bookEntity, Integer quantity) {
        this.orderEntity = orderEntity;
        this.bookEntity = bookEntity;
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
