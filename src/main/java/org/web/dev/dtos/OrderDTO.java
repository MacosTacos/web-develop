package org.web.dev.dtos;

import org.web.dev.domain.OrderStatus;
import org.web.dev.domain.entities.OrderContentEntity;
import org.web.dev.domain.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;
    private OrderStatus status;
    private Double price;
    private List<OrderContentDTO> orderContentDTOS;
    private UserDTO userDTO;

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDateTime dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OrderContentDTO> getOrderContentDTOS() {
        return orderContentDTOS;
    }

    public void setOrderContentDTOS(List<OrderContentDTO> orderContentDTOS) {
        this.orderContentDTOS = orderContentDTOS;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
