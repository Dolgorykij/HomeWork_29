package ru.hogwarts.school_HomeWork_29.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new WrongNameOrAgeException("Wrong name/age");
        }
        if (student.getAge() <= 0) {
            throw new WrongNameOrAgeException("Wrong name/age");
        }
            return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFound("Student not found"));
    }

    public Collection<Student> findAll () {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFound("Student not found");
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFound("Student not found");
        }
        studentRepository.deleteById(id);
    }

    public Collection<Student> sortByAge(int age) {
        return studentRepository.findStudentByAge(age);
        //return students.values().stream()
                //.filter(student -> student.getAge() == age)
                //.collect(Collectors.toList());
    }
    public Collection<Student> findByAgeBetween (int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> findAllByFaculty (Long id) {
        return studentRepository.findAllByFaculty_id(id);
    }

    public Integer getAllStudents () {
        return studentRepository.getAllStudents();
    }

    public Integer getAverageAge () {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents () {
        return studentRepository.getLastFiveStudents();
    }
    //private Sort sortByAgeHelp () {
      //  return new Sort();
    //}
    //
}