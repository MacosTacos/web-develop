package org.web.dev.exceptions.order;

public class NotEnoughStockException extends OrderException{
    public NotEnoughStockException(String message) {
        super(message);
    }
}
