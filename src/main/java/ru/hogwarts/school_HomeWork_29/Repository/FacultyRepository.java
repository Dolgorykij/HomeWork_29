package ru.hogwarts.school_HomeWork_29.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
//
    Collection<Faculty> findByColor (String color);
    Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase (String name, String color);
    //Collection<Faculty> findByStudent_id (Long id);
}
