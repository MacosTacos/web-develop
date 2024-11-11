package org.web.dev.exceptions.order;

public class OrderStatusChangeException extends OrderException {
    public OrderStatusChangeException(String message) {
        super(message);
    }
}
