package org.web.dev.domain.entities;

import jakarta.persistence.*;
import org.web.dev.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;
    private OrderStatus status;
    private List<OrderContentEntity> orderContentEntities;
    private StoreEntity storeEntity;

    protected OrderEntity() {
    }

    public OrderEntity(LocalDateTime dateOfCreation, OrderStatus status, List<OrderContentEntity> orderContentEntities, StoreEntity storeEntity) {
        this.dateOfCreation = dateOfCreation;
        this.status = status;
        this.storeEntity = storeEntity;
    }

    @Column(name = "date_of_creation")
    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Column(name = "date_of_completion")
    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDateTime dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<OrderContentEntity> getOrderContentEntities() {
        return orderContentEntities;
    }

    public void setOrderContentEntities(List<OrderContentEntity> orderContentEntities) {
        this.orderContentEntities = orderContentEntities;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    public StoreEntity getStoreEntity() {
        return storeEntity;
    }

    public void setStoreEntity(StoreEntity storeEntity) {
        this.storeEntity = storeEntity;
    }

}
