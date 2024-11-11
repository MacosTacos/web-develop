package org.web.dev.services;

import org.springframework.http.ResponseEntity;
import org.web.dev.domain.OrderStatus;
import org.web.dev.dtos.OrderDTO;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO);
    ResponseEntity<List<OrderDTO>> findAllActive();
    ResponseEntity<OrderDTO> findById(Long id);
    ResponseEntity<OrderDTO> updateOrderStatus(Long id, OrderStatus orderStatus);
}
