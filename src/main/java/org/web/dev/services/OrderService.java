package org.web.dev.services;

import org.springframework.http.ResponseEntity;
import org.web.dev.domain.enums.OrderStatus;
import org.web.dev.dtos.OrderDTO;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    void createOrder(Principal principal);
    ResponseEntity<List<OrderDTO>> findAllActive();
    ResponseEntity<OrderDTO> findById(Long id);
    ResponseEntity<OrderDTO> updateOrderStatus(Long id, OrderStatus orderStatus);
}
