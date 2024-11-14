package ru.hogwarts.school_HomeWork_29.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAge (int age);

    Collection<Student> findByAgeBetween (int min, int max);

    Collection<Student> findAllByFaculty_id (Long id);

}
