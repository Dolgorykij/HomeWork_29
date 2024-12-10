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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest{

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @BeforeEach
    public void clearDB() {
        studentRepository.deleteAll();
    }

    @Test
    public void contextLoads () throws Exception{
        assertThat(studentRepository).isNotNull();
    }
    @Test
    void addStudentTest() {
        Student student = new Student();
        student.setName("Veresk");
        student.setAge(20);
        student.setId(1L);

        System.out.println(student);
        System.out.println(port);
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );
        System.out.println(studentResponseEntity.getBody());
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actual = studentResponseEntity.getBody();
        assertNotNull(actual);
        assertNotNull(student.getId());
        assertEquals(student.getName(), actual.getName());
        assertThat(actual.getAge()).isEqualTo(student.getAge());

    }

    @Test
    void deleteStudentTest() {
        Student student = new Student();
        student.setName("Veresk");
        student.setAge(20);
        student.setId(1L);
        student = studentRepository.save(student);

        ResponseEntity<Student> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Student.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void GetStudentTest() {
        Student student = new Student();
        student.setName("Veresk");
        student.setAge(20);
        student.setId(1L);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(),HttpStatusCode.valueOf(200));
        Student actual = studentResponseEntity.getBody();
        assertEquals(student.getName(),actual.getName());
        assertEquals(student.getId(),actual.getId());
        assertEquals(student.getAge(),actual.getAge());
    }
    @Test
    void editStudentTest() {
        Student student = new Student();
        student.setName("Veresk");
        student.setAge(20);
        student.setId(1L);
        student = studentRepository.save(student);

        student.setName("secondName");
        student.setAge(21);

        ResponseEntity<Student> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Student secondStudent = response.getBody();
        assertNotNull(secondStudent);
        assertEquals("secondName", secondStudent.getName());
        assertEquals(21, secondStudent.getAge());

        Student expectedStudent = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals("secondName", expectedStudent.getName());
        assertEquals(21, expectedStudent.getAge());
    }
}
