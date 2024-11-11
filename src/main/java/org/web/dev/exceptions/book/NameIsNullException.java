package org.web.dev.exceptions.book;

public class NameIsNullException extends BookException {
    public NameIsNullException(String message) {
        super(message);
    }
}
