package ru.hogwarts.school_HomeWork_29.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.FacultyDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        facultyRepository.deleteAll();
    }

    @Test
    void addFacultyTest() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setName("Gryffindor");
        facultyDTO.setColor("Red");

        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                facultyDTO,
                Faculty.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
        assertEquals("Red", response.getBody().getColor());
    }

    @Test
    void getFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
        assertEquals("Red", response.getBody().getColor());
    }

    @Test
    void deleteFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
        faculty = facultyRepository.save(faculty);

        restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId());

        assertFalse(facultyRepository.existsById(faculty.getId()));
    }
}
