package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity {

    private String name;
    private List<StoreEntity> storeEntities;

    protected CityEntity() {
    }

    public CityEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "cityEntity", fetch = FetchType.LAZY)
    public List<StoreEntity> getStoreEntities() {
        return storeEntities;
    }

    public void setStoreEntities(List<StoreEntity> storeEntities) {
        this.storeEntities = storeEntities;
    }
}
