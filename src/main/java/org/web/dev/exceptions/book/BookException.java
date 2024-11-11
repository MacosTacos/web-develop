package org.web.dev.exceptions.book;

public abstract class BookException extends RuntimeException {
    public BookException(String message) {
        super(message);
    }
}
