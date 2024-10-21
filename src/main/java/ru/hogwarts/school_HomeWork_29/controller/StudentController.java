package ru.hogwarts.school_HomeWork_29.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent (@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent (@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("{id}")
    public Student getStudent (@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PutMapping
    public Student editStudent (@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> sortByAge(@RequestParam int age) {
        List<Student> studentsByAge = studentService.sortByAge(age);
        return ResponseEntity.ok(studentsByAge);
    }


}
