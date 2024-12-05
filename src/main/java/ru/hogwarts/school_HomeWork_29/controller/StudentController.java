package ru.hogwarts.school_HomeWork_29.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.service.StudentService;

import java.util.Collection;
import java.util.List;


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
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
//    @GetMapping("{id}/faculty")
//    public Faculty getStudentFaculty(@PathVariable Long id) {
//        Student student = studentService.findStudent(id);
//        return student.getFaculty();
//    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<Integer> getAllStudents() {
        Integer count = studentService.getAllStudents();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/getAverageAge")
    public ResponseEntity<Integer> getAverageAGe() {
        Integer average = studentService.getAverageAge();
        return ResponseEntity.ok(average);
    }
    @GetMapping("/getLastFiveStudents")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> lastStudents = studentService.getLastFiveStudents();
        return ResponseEntity.ok(lastStudents);
    }

    @ExceptionHandler(StudentNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(StudentNotFound e) {
        return e.getMessage();
    }
    @ExceptionHandler(WrongNameOrAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(WrongNameOrAgeException e) {
        return e.getMessage();
    }


        //List<Student> studentsByAge = studentService.sortByAge(age);
        //return ResponseEntity.ok(studentsByAge);
    //}
//

}
