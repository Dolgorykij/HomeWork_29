package ru.hogwarts.school_HomeWork_29.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.model.StudentDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;
    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(17);

        studentDTO = new StudentDTO();
        studentDTO.setName("Harry Potter");
        studentDTO.setAge(17);
    }

    @Test
    void addStudentTest() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.addStudent(studentDTO);
        assertNotNull(result);
        assertEquals("Harry Potter", result.getName());
        assertEquals(17, result.getAge());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void addStudentNameIsBlankTest() {
        studentDTO.setName("");
        assertThrows(WrongNameOrAgeException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void addStudentAgeIsZeroTest() {
        studentDTO.setAge(0);
        assertThrows(WrongNameOrAgeException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void findStudentTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result = studentService.findStudent(1L);
        assertNotNull(result);
        assertEquals("Harry Potter", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void findStudentNotExistTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFound.class, () -> studentService.findStudent(1L));
    }

    @Test
    void findAllTest() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        Collection<Student> result = studentService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void editStudentTest() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.editStudent(student);
        assertNotNull(result);
        assertEquals("Harry Potter", result.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void editStudentNotExistTest() {
        when(studentRepository.existsById(1L)).thenReturn(false);
        assertThrows(StudentNotFound.class, () -> studentService.editStudent(student));
    }

    @Test
    void deleteStudentTest() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudentNotExistTest() {
        when(studentRepository.existsById(1L)).thenReturn(false);
        assertThrows(StudentNotFound.class, () -> studentService.deleteStudent(1L));
    }

    @Test
    void sortByAgeTest() {
        when(studentRepository.findStudentByAge(17)).thenReturn(Arrays.asList(student));
        Collection<Student> result = studentService.sortByAge(17);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findStudentByAge(17);
    }

    @Test
    void findByAgeBetweenTest() {
        when(studentRepository.findByAgeBetween(16, 18)).thenReturn(Arrays.asList(student));
        Collection<Student> result = studentService.findByAgeBetween(16, 18);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findByAgeBetween(16, 18);
    }

    @Test
    void getAverageAgeTest() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        double result = studentService.getAverageAgeByStream();
        assertEquals(17.0, result);
        verify(studentRepository, times(1)).findAll();
    }
}
