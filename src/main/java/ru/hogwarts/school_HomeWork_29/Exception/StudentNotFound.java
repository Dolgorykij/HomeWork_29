package ru.hogwarts.school_HomeWork_29.Exception;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound() {
    }

    public StudentNotFound(String message) {
        super(message);
    }

    public StudentNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFound(Throwable cause) {
        super(cause);
    }
}
