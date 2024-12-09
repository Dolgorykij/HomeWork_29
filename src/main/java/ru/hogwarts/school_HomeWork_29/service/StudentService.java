package ru.hogwarts.school_HomeWork_29.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.model.StudentDTO;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);


    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(StudentDTO studentDTO) {
        if (studentDTO.getName() == null || studentDTO.getName().isBlank()) {
            logger.warn("Некорректное имя: {}", studentDTO.getName());
            throw new WrongNameOrAgeException("Wrong name/age");
        }
        if (studentDTO.getAge() <= 0) {
            logger.warn("Некорректный возраст: {}", studentDTO.getAge());
            throw new WrongNameOrAgeException("Wrong name/age");
        }
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        logger.info("Студент добавлен: {}",student);
            return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> { logger.warn("Студент с таким ID {} not found", id);
                    return new StudentNotFound("Student not found");
                });
    }

    public Collection<Student> findAll () {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            logger.warn("Студент с таким ID {} not found", student.getId());
            throw new StudentNotFound("Student not found");
        }
        logger.info("Студент {} был изменен", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            logger.warn("Студент с таким ID {} not found", id);
            throw new StudentNotFound("Student not found");
        }
        logger.info("Студент с ID {} был удален", id);
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