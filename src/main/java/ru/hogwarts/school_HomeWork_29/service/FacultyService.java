package ru.hogwarts.school_HomeWork_29.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
   private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty (long id) {
        return facultyRepository.findById(id).orElseThrow();
    }

    public Faculty editFaculty (Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> sortByColor(String color) {
        return facultyRepository.findByColor(color);
        //return faculties.values().stream()
          //      .filter(faculty -> faculty.getColor().equals(color))
            //    .collect(Collectors.toList());
    }
}
