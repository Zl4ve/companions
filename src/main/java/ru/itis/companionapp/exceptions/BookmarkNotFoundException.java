package ru.itis.companionapp.exceptions;


public class BookmarkNotFoundException extends NotFoundException {
    public BookmarkNotFoundException() {
        super();
    }

    public BookmarkNotFoundException(String message) {
        super(message);
    }

    public BookmarkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookmarkNotFoundException(Throwable cause) {
        super(cause);
    }
}
