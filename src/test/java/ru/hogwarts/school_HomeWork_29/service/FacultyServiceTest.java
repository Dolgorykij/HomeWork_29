package ru.hogwarts.school_HomeWork_29.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school_HomeWork_29.Exception.FacultyNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrColorException;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.FacultyDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    private Faculty faculty;
    private FacultyDTO facultyDTO;

    @BeforeEach
    void setUp() {
        faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        facultyDTO = new FacultyDTO();
        facultyDTO.setName("Gryffindor");
        facultyDTO.setColor("Red");
    }

    @Test
    void addFacultyTest() {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        Faculty result = facultyService.addFaculty(facultyDTO);
        assertNotNull(result);
        assertEquals("Gryffindor", result.getName());
        assertEquals("Red", result.getColor());
        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void addFacultyNameIsBlankTest() {
        facultyDTO.setName("");
        assertThrows(WrongNameOrColorException.class, () -> facultyService.addFaculty(facultyDTO));
    }

    @Test
    void addFacultyColorIsBlankTest() {
        facultyDTO.setColor("");
        assertThrows(WrongNameOrColorException.class, () -> facultyService.addFaculty(facultyDTO));
    }

    @Test
    void findFacultyTest() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        Faculty result = facultyService.findFaculty(1L);
        assertNotNull(result);
        assertEquals("Gryffindor", result.getName());
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    void findFacultyNotExistTest() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FacultyNotFound.class, () -> facultyService.findFaculty(1L));
    }

    @Test
    void editFacultyTest() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        Faculty result = facultyService.editFaculty(faculty);
        assertNotNull(result);
        assertEquals("Gryffindor", result.getName());
        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void editFacultyNotExistTest() {
        when(facultyRepository.existsById(1L)).thenReturn(false);
        assertThrows(FacultyNotFound.class, () -> facultyService.editFaculty(faculty));
    }

    @Test
    void deleteFacultyTest() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        facultyService.deleteFaculty(1L);
        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFacultyNotExistTest() {
        when(facultyRepository.existsById(1L)).thenReturn(false);
        assertThrows(FacultyNotFound.class, () -> facultyService.deleteFaculty(1L));
    }

    @Test
    void sortByColorTest() {
        when(facultyRepository.findByColor("Red")).thenReturn(Arrays.asList(faculty));
        Collection<Faculty> result = facultyService.sortByColor("Red");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(facultyRepository, times(1)).findByColor("Red");
    }

}