package org.web.dev.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.web.dev.domain.enums.OrderStatus;
import org.web.dev.services.OrderService;

import java.security.Principal;

@Controller
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String createOrder(Principal principal) {
        LOG.info("POST:/orders/create Order create request from " + principal.getName());
        orderService.createOrder(principal);
        return "main/order-created-page.html";
    }

    @Override
    public String getOrder(Long id, Principal principal) {
        LOG.info("GET:/orders/get/{} Order create request from {}", id, principal.getName());
        orderService.findById(id);
        return "main/order-created-page.html";
    }

    @Override
    public String completeOrder(Long id, Principal principal) {
        LOG.info("POST:/orders/complete/{} Order complete request from {}", id, principal.getName());
        orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
        return "main/order-created-page.html";
    }

    @Override
    public String cancelOrder(Long id, Principal principal) {
        LOG.info("POST:/cancel/get/{} Order cancel request from {}", id, principal.getName());
        orderService.updateOrderStatus(id, OrderStatus.CANCELED);
        return "main/order-created-page.html";
    }
}
