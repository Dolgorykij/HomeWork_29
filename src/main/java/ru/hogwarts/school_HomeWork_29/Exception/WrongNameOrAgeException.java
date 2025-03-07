package ru.hogwarts.school_HomeWork_29.Exception;

public class WrongNameOrAgeException extends RuntimeException {
    public WrongNameOrAgeException() {
    }

    public WrongNameOrAgeException(String message) {
        super(message);
    }

    public WrongNameOrAgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongNameOrAgeException(Throwable cause) {
        super(cause);
    }
}
