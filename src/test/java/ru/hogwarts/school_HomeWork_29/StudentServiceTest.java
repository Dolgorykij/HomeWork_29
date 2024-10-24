package ru.hogwarts.school_HomeWork_29;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentService studentService;

    // @BeforeEach
    //public void setUp() {
    //  studentService = new StudentService();
    //}

    @Test
    public void testAddStudent() {
        Student testStudent = new Student(0L,"Harry",21);
        Student addedStudent = studentService.addStudent(testStudent);
        assertEquals("Harry", addedStudent.getName());
        assertEquals(21, addedStudent.getAge());
    }

    @Test
    public void testFindStudent() {
        Student testStudent = new Student(0L,"Harry",21);
        Student addedStudent = studentService.addStudent(testStudent);
        Student foundStudent = studentService.findStudent(addedStudent.getId());
        assertEquals(addedStudent.getId(), foundStudent.getId());
        assertEquals("Harry", foundStudent.getName());
        assertEquals(21, foundStudent.getAge());
    }

    @Test
    public void testEditStudent() {
        Student testStudent = new Student(0L,"Harry",21);
        Student addedStudent = studentService.addStudent(testStudent);
        addedStudent.setName("Витек");
        addedStudent.setAge(42);
        Student editedStudent = studentService.editStudent(addedStudent);
        assertEquals("Витек", editedStudent.getName());
        assertEquals(42, editedStudent.getAge());
        assertEquals(addedStudent.getId(), editedStudent.getId());
    }

    //@Test
    //public void testDeleteStudent() {
    //  Student testStudent = new Student(0L,"Harry",21);
    //  Student addedStudent = studentService.addStudent(testStudent);
    //  Student deletedStudent = studentService.deleteStudent(addedStudent.getId());
    //  assertEquals("Harry", deletedStudent.getName());
    //}

    @Test
    public void testSortByAge() {
        Student student1 = new Student(0L,"Harry",18);
        Student student2 = new Student(0L,"Витек",18);
        Student student3 = new Student(0L,"Кобзон",3200);
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        List<Student> testAge = studentService.sortByAge(18);
        assertEquals(2, testAge.size());
    }
}
