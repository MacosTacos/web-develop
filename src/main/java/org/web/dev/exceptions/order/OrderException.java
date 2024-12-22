package org.web.dev.exceptions.order;

public abstract class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
