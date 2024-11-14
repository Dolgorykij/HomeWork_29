package ru.hogwarts.school_HomeWork_29.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_HomeWork_29.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
//
    Optional<Avatar> findByStudentId (Long studentId);
}
