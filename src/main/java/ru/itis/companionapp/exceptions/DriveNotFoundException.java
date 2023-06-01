package ru.itis.companionapp.exceptions;

public class DriveNotFoundException extends NotFoundException {
    public DriveNotFoundException() {
        super();
    }

    public DriveNotFoundException(String message) {
        super(message);
    }

    public DriveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriveNotFoundException(Throwable cause) {
        super(cause);
    }
}
