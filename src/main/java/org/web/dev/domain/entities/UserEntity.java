package org.web.dev.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private List<RoleEntity> roles;
    private List<OrderEntity> orderEntities;

    protected UserEntity() {
    }

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserEntity(String name, String email, String password, List<RoleEntity> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String login) {
        this.email = login;
    }

    public String getPassword() {
        return password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY)
    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
