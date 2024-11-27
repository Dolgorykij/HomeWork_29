package ru.hogwarts.school_HomeWork_29.controller;

import org.springframework.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerInsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @BeforeEach
    public void clearDB() {
        facultyRepository.deleteAll();
    }

    @Test
    public void contextLoads () throws Exception{
        assertThat(facultyRepository).isNotNull();
    }
    @Test
    void addFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Veresk");
        faculty.setColor("Veresk");
        faculty.setId(1L);

        System.out.println(faculty);
        System.out.println(port);
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );
        System.out.println(facultyResponseEntity.getBody());
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actual = facultyResponseEntity.getBody();
        assertNotNull(actual);
        //assertNotNull(faculty.getId());
        assertEquals(faculty.getName(), actual.getName());
        assertThat(actual.getColor()).isNotEmpty().isEqualTo(faculty.getColor());

    }

    @Test
    void deleteFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Veresk");
        faculty.setColor("Veresk");
        faculty.setId(1L);
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void GetFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Veresk");
        faculty.setColor("Veresk");
        faculty.setId(1L);
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),HttpStatusCode.valueOf(200));
        Faculty actual = facultyResponseEntity.getBody();
        assertEquals(faculty.getName(),actual.getName());
        assertEquals(faculty.getId(),actual.getId());
        assertEquals(faculty.getColor(),actual.getColor());
    }
    @Test
    void editFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("firstName");
        faculty.setColor("firstColor");
        faculty = facultyRepository.save(faculty);

        faculty.setName("secondName");
        faculty.setColor("secondColor");

        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Faculty secondFaculty = response.getBody();
        assertNotNull(secondFaculty);
        assertEquals("secondName", secondFaculty.getName());
        assertEquals("secondColor", secondFaculty.getColor());

        Faculty expectedFaculty = facultyRepository.findById(faculty.getId()).orElseThrow();
        assertEquals("secondName", expectedFaculty.getName());
        assertEquals("secondColor", expectedFaculty.getColor());
    }
}