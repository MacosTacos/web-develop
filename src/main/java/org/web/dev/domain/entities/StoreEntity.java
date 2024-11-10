package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stores")
public class StoreEntity extends BaseEntity {

    private String address;
    private CityEntity cityEntity;
    private List<OrderEntity> orderEntities;

    protected StoreEntity() {
    }

    public StoreEntity(String address, CityEntity cityEntity) {
        this.address = address;
        this.cityEntity = cityEntity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    public CityEntity getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    @OneToMany(mappedBy = "storeEntity", fetch = FetchType.LAZY)
    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
