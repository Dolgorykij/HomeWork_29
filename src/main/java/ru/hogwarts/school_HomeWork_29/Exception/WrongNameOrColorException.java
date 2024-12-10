package ru.hogwarts.school_HomeWork_29.Exception;

public class WrongNameOrColorException extends RuntimeException {
    public WrongNameOrColorException(Throwable cause) {
        super(cause);
    }

    public WrongNameOrColorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongNameOrColorException(String message) {
        super(message);
    }

    public WrongNameOrColorException() {
    }
}
