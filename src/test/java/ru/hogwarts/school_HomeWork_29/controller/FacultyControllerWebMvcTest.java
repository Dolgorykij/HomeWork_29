package ru.hogwarts.school_HomeWork_29.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school_HomeWork_29.Repository.AvatarRepository;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.service.AvatarService;
import ru.hogwarts.school_HomeWork_29.service.FacultyService;
import ru.hogwarts.school_HomeWork_29.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;

    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void addFacultyTest () throws Exception {
        final String name = "1";
        final String color = "white";
        final Long id = 1L;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post
                                ("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    public void addFacultyBadTest() throws Exception {
        JSONObject badFacultyObject = new JSONObject();
        badFacultyObject.put("name", "");
        badFacultyObject.put("color", -1);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(badFacultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Wrong name/color"));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        final Long id = 1L;

        doNothing().when(facultyRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", id))
                .andExpect(status().isNoContent());

        verify(facultyRepository, times(1)).deleteById(id);
    }
    @Test
    public void deleteFacultyBadTest() throws Exception {
        final Long badID = 100L;

        when(facultyRepository.existsById(badID)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{id}", badID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Faculty not found"));


        verify(facultyRepository, times(1)).existsById(badID);
        verify(facultyRepository, never()).deleteById(badID);
    }

    @Test
    public void getFacultyTest() throws Exception {
        final String name = "1";
        final String color = "white";
        final Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    public void getFacultyBadTest() throws Exception {
        final Long badId = 100L;

        when(facultyRepository.findById(badId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", badId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Faculty not found"));

        verify(facultyRepository, times(1)).findById(badId);
    }

    @Test
    public void editFacultyTest() throws Exception {
        final String name = "1";
        final String color = "white";
        final Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName("2");
        faculty.setColor("Brown");

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(id);
        updatedFaculty.setName(name);
        updatedFaculty.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(updatedFaculty));

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    public void editFacultyBadTest() throws Exception {
        final Long badId = 100L;
        final String name = "badName";
        final String color = "badColor";

        Faculty faculty = new Faculty();
        faculty.setId(badId);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(badId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(new ObjectMapper().writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Faculty not found"));

        verify(facultyRepository, times(0)).save(any(Faculty.class));
    }
}
