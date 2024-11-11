package org.web.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web.dev.domain.OrderStatus;
import org.web.dev.dtos.OrderDTO;
import org.web.dev.services.OrderService;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.createOrder(orderDTO);
        return responseEntity;
    }

    @GetMapping(path = "get/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.findById(id);
        return responseEntity;
    }

    @PatchMapping(path = "/complete")
    public ResponseEntity<OrderDTO> completeOrder(@RequestParam Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
        return responseEntity;
    }

    @PatchMapping(path = "/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@RequestParam Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.updateOrderStatus(id, OrderStatus.CANCELED);
        return responseEntity;
    }
}
