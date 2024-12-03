package ru.hogwarts.school_HomeWork_29.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
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

    @GetMapping("sortByColor")
    public Collection<Faculty> SortByColor(@RequestParam String color) {
        return facultyService.sortByColor(color);
    }
    @GetMapping("byFaculty")
    public Collection<Faculty> findByNameOrColor (@RequestParam String name,@RequestParam String color) {
        return facultyService.findByNameOrColor(name, color);
    }
//    @GetMapping("{id}/students")
//    public Collection<Student> getFacultyStudents(@PathVariable Long id) {
//        Faculty faculty = facultyService.findFaculty(id);
//        return faculty.getStudents();
//    }
    //@GetMapping("byStudent")
    //public Collection<Faculty> findByStudent_id (@RequestParam Long id) {
       // return facultyService.findByStudent_id(id);
   // }
//
}
