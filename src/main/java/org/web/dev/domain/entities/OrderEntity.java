package org.web.dev.domain.entities;

import jakarta.persistence.*;
import org.web.dev.domain.enums.OrderStatus;
import org.web.dev.exceptions.order.NotEnoughStockException;
import org.web.dev.exceptions.order.OrderStatusChangeException;
import org.web.dev.exceptions.order.RequiredOrderFieldIsNullException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;
    private OrderStatus status;
    private Double price;
    private List<OrderContentEntity> orderContentEntities;
    private UserEntity userEntity;

    protected OrderEntity() {
    }

    public OrderEntity(UserEntity userEntity) {
        setUserEntity(userEntity);
        this.dateOfCreation = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
        this.price = 0d;
        this.orderContentEntities = new ArrayList<>();
    }

    public void setOrderContent(BookEntity bookEntity, int quantity) {
        OrderContentEntity orderContentEntity = new OrderContentEntity(
                this,
                bookEntity,
                quantity
        );
        orderContentEntities.add(orderContentEntity);
        this.price += bookEntity.getPrice() * orderContentEntity.getQuantity();
        this.price = Math.floor(price * 100) / 100;
    }

    public void changeStatus(OrderStatus orderStatus) {
        if (status.equals(OrderStatus.CREATED)) {
            switch (orderStatus) {
                case COMPLETED -> {
                    this.dateOfCompletion = LocalDateTime.now();
                    this.status = OrderStatus.COMPLETED;
                }
                case CANCELED -> {
                    this.status = OrderStatus.CANCELED;
                }
                default -> {
                    throw new OrderStatusChangeException("Invalid order status");
                }
            }
        } else {
            throw new OrderStatusChangeException("Cannot change order status. Current status: " + status);
        }
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfCompletion(LocalDateTime dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOrderContentEntities(List<OrderContentEntity> orderContentEntities) {
        this.orderContentEntities = orderContentEntities;
    }

    @Column(name = "date_of_creation", nullable = false)
    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    @Column(name = "date_of_completion")
    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public OrderStatus getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<OrderContentEntity> getOrderContentEntities() {
        return orderContentEntities;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    private void setUserEntity(UserEntity userEntity) {
        if (userEntity != null) {
            this.userEntity = userEntity;
        } else {
            throw new RequiredOrderFieldIsNullException("UserEntity cannot be null");
        }
    }
}
