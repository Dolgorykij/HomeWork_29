package ru.hogwarts.school_HomeWork_29.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.model.StudentDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void addStudentTest() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Harry Potter");
        studentDTO.setAge(11);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                studentDTO,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getName());
        assertEquals(11, response.getBody().getAge());
    }

    @Test
    void getStudentTest() {
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(11);
        student = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getName());
        assertEquals(11, response.getBody().getAge());
    }

    @Test
    void deleteStudentTest() {
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(11);
        student = studentRepository.save(student);

        restTemplate.delete("http://localhost:" + port + "/student/" + student.getId());

        assertFalse(studentRepository.existsById(student.getId()));
    }
}
