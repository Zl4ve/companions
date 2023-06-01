package ru.itis.companionapp.exceptions;

public class ReviewNotByUserException extends RuntimeException {
    public ReviewNotByUserException() {
        super();
    }

    public ReviewNotByUserException(String message) {
        super(message);
    }

    public ReviewNotByUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewNotByUserException(Throwable cause) {
        super(cause);
    }
}
