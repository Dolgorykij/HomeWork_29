package ru.hogwarts.school_HomeWork_29.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty addFaculty (@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty (@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("{id}")
    public Faculty getFaculty (@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping
    public Faculty editFaculty (@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> SortByColor(@RequestParam String color) {
        List<Faculty> facultyColor = facultyService.sortByColor(color);
        return ResponseEntity.ok(facultyColor);
    }
}
