package ru.hogwarts.school_HomeWork_29.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAge (int age);

    Collection<Student> findByAgeBetween (int min, int max);
//
    Collection<Student> findAllByFaculty_id (Long id);

    @Query(value = "SELECT count(*) FROM Student", nativeQuery = true)
    Integer getAllStudents();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Integer getAverageAge ();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();




}
