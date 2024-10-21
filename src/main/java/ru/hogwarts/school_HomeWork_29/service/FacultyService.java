package ru.hogwarts.school_HomeWork_29.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private Long facultyId = 0L;

    public Faculty addFaculty (Faculty faculty) {
        faculty.setId(++facultyId);
        faculties.put(facultyId, faculty);
        return faculty;
    }

    public Faculty findFaculty (long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty (Faculty faculty) {
        faculties.put(faculty.getId(),faculty);
        return faculty;
    }

    public Faculty deleteFaculty (long id) {
        return faculties.remove(id);
    }

    public List<Faculty> sortByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
