package ru.itis.companionapp.exceptions;

public class PreferenceNotFoundException extends NotFoundException {
    public PreferenceNotFoundException() {
        super();
    }

    public PreferenceNotFoundException(String message) {
        super(message);
    }

    public PreferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreferenceNotFoundException(Throwable cause) {
        super(cause);
    }
}
