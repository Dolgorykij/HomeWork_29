package ru.hogwarts.school_HomeWork_29.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor (String color);
}
