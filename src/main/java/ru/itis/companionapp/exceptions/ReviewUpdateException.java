package ru.itis.companionapp.exceptions;

public class ReviewUpdateException extends RuntimeException {
    public ReviewUpdateException() {
        super();
    }

    public ReviewUpdateException(String message) {
        super(message);
    }

    public ReviewUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewUpdateException(Throwable cause) {
        super(cause);
    }
}
