package org.web.dev.domain.entities;

import jakarta.persistence.*;
import org.web.dev.domain.enums.UserRole;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    private UserRole name;

    protected RoleEntity() {
    }

    public RoleEntity(UserRole name) {
        this.name = name;
    }

    @Column(name = "role", unique = true)
    @Enumerated(EnumType.STRING)
    public UserRole getName() {
        return name;
    }

    public void setName(UserRole userRole) {
        this.name = userRole;
    }
}
