package ru.hogwarts.school_HomeWork_29.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_HomeWork_29.Exception.FacultyNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrColorException;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.FacultyDTO;
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
    public Faculty addFaculty (@RequestBody FacultyDTO facultyDTO) {
        return facultyService.addFaculty(facultyDTO);
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
    @GetMapping("/getLongestFaculty")
    public ResponseEntity<Faculty> getLongestFaculty() {
        Faculty faculty = facultyService.getLongestFaculty();
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/sum")
    public ResponseEntity<Integer> sum() {
        int sum = facultyService.sum();
        return ResponseEntity.ok(sum);
    }

    @ExceptionHandler(FacultyNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(FacultyNotFound e) {
        return e.getMessage();
    }
    @ExceptionHandler(WrongNameOrColorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(WrongNameOrColorException e) {
        return e.getMessage();
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
