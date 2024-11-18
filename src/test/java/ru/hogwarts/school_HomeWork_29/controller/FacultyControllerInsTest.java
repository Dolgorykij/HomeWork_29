package ru.hogwarts.school_HomeWork_29.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    private TestRestTemplate testRestTemplate;


    //@BeforeEach
    //public void clearDB() {
       // facultyRepository.deleteAll();
       // Faculty faculty = new Faculty();
       // faculty.setName("Veresk");
       // faculty.setColor("Veresk");
   // }

    @Test
    public void contextLoads () throws Exception{
        assertThat(facultyRepository).isNotNull();
    }

    @Test
    public void addFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Veresk");
        faculty.setColor("Veresk");

        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actual = facultyResponseEntity.getBody();
        assertNotNull(actual);
        assertNotNull(faculty.getId());
        assertEquals(faculty.getName(), actual.getName());
        assertThat(actual.getColor()).isNotEmpty().isEqualTo(faculty.getColor());

    }
}