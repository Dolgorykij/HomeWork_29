package ru.hogwarts.school_HomeWork_29.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.service.AvatarService;
import ru.hogwarts.school_HomeWork_29.service.StudentService;

import java.io.IOException;
import java.util.Collection;


@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student addStudent (@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent (@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("{id}")
    public Student getStudent (@PathVariable Long id) {
        return studentService.findStudent(id);
    }
    @GetMapping
    public Collection<Student> findAll (){
        return studentService.findAll();
    }

    @PutMapping
    public Student editStudent (@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @GetMapping("byAgeBetween")
    public Collection<Student> findByAgeBetween (@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("sortByAge")
    public Collection<Student> sortByAge (@RequestParam int age) {
        return studentService.sortByAge(age);
    }
    @GetMapping("{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        return student.getFaculty();
    }

        //List<Student> studentsByAge = studentService.sortByAge(age);
        //return ResponseEntity.ok(studentsByAge);
    //}
//

}
