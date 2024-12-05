package ru.hogwarts.school_HomeWork_29.Exception;

public class FacultyNotFound extends RuntimeException {
    public FacultyNotFound() {
    }

    public FacultyNotFound(String message) {
        super(message);
    }

    public FacultyNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyNotFound(Throwable cause) {
        super(cause);
    }
}
