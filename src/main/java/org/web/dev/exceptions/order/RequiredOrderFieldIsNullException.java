package org.web.dev.exceptions.order;

public class RequiredOrderFieldIsNullException extends OrderException {
    public RequiredOrderFieldIsNullException(String message) {
        super(message);
    }
}
