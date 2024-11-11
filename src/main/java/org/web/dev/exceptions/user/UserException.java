package org.web.dev.exceptions.user;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
