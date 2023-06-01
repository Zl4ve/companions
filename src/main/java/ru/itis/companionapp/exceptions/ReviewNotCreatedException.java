package ru.itis.companionapp.exceptions;

public class ReviewNotCreatedException extends RuntimeException {
    public ReviewNotCreatedException() {
        super();
    }

    public ReviewNotCreatedException(String message) {
        super(message);
    }

    public ReviewNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewNotCreatedException(Throwable cause) {
        super(cause);
    }
}
