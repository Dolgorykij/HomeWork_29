package ru.hogwarts.school_HomeWork_29.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.Exception.StudentNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrAgeException;
import ru.hogwarts.school_HomeWork_29.Repository.StudentRepository;
import ru.hogwarts.school_HomeWork_29.model.Student;
import ru.hogwarts.school_HomeWork_29.model.StudentDTO;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            logger.error("Некорректное имя: {}", studentDTO.getName());
            throw new WrongNameOrAgeException("Wrong name/age");
        }
        if (studentDTO.getAge() <= 0) {
            logger.error("Некорректный возраст: {}", studentDTO.getAge());
            throw new WrongNameOrAgeException("Wrong name/age");
        }
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        logger.info("Студент добавлен: {}",student.getName());
            return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> { logger.error("Студент с таким ID {} not found", id);
                    return new StudentNotFound("Student not found");
                });
    }

    public Collection<Student> findAll () {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            logger.error("Студент с таким ID {} not found", student.getId());
            throw new StudentNotFound("Student not found");
        }
        logger.info("Студент {} был изменен", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            logger.error("Студент с таким ID {} not found", id);
            throw new StudentNotFound("Student not found");
        }
        logger.info("Студент с ID {} был удален", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> sortByAge(int age) {
        logger.info("Студенты со следующим возрастом: {}", age);
        return studentRepository.findStudentByAge(age);
        //return students.values().stream()
                //.filter(student -> student.getAge() == age)
                //.collect(Collectors.toList());
    }
    public Collection<Student> findByAgeBetween (int min, int max) {
        logger.info("Студенты с возрастом от {} и до {}", min,max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> findAllByFaculty (Long id) {
        logger.info("Студенты учащиеся на факультете со следующим номером: {}", id);
        return studentRepository.findAllByFaculty_id(id);
    }

    public Integer getAllStudents () {
        logger.info("Список всех студентов: ");
        return studentRepository.getAllStudents();
    }

    public Integer getAverageAge () {
        logger.info("Avarage age of students: ");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents () {
        logger.info("Последние 5 студентов: ");
        return studentRepository.getLastFiveStudents();
    }

    public List<Student> getStudStartWithA () {
        return studentRepository.findAll().stream()
                .filter(student -> student.getName().startsWith("A"))
                .sorted(Comparator.comparing(student ->
                        student.getName().substring(0, 1).
                                toUpperCase() + student.getName().substring(1).toLowerCase()
                ))
                .collect(Collectors.toList());
    }
    public double getAverageAgeByStream() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow();
    }
    //private Sort sortByAgeHelp () {
      //  return new Sort();
    //}
    //
}