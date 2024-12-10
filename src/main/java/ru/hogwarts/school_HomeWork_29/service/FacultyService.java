package ru.hogwarts.school_HomeWork_29.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school_HomeWork_29.Exception.FacultyNotFound;
import ru.hogwarts.school_HomeWork_29.Exception.WrongNameOrColorException;
import ru.hogwarts.school_HomeWork_29.Repository.FacultyRepository;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.model.FacultyDTO;

import java.util.*;


@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
   private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(FacultyDTO facultyDTO) {
        if (facultyDTO.getName() == null || facultyDTO.getName().isBlank()) {
            logger.error("Некорректное имя: {}", facultyDTO.getName());
            throw new WrongNameOrColorException("Wrong name/color");
        }
        if (facultyDTO.getColor() == null || facultyDTO.getColor().isBlank()) {
            logger.error("Некорректный цвет: {}", facultyDTO.getColor());
            throw new WrongNameOrColorException("Wrong name/color");
        }
        Faculty faculty = new Faculty();
        faculty.setName(facultyDTO.getName());
        faculty.setColor(facultyDTO.getColor());
        logger.info("Факультет добавлен: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty (long id) {
        return facultyRepository.findById(id).orElseThrow(() -> { logger.error("Факультет с таким ID {} not found", id);
        return new FacultyNotFound("Faculty not found");
        });
    }

    public Faculty editFaculty (Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.error("Факультет с таким ID {} not found", faculty.getId());
            throw new FacultyNotFound("Faculty not found");
        }
        logger.info("Факультет {} был изменен", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (long id) {
        if (!facultyRepository.existsById(id)) {
            logger.error("Факультет с таким ID {} not found", id);
            throw new FacultyNotFound("Faculty not found");
        }
        logger.info("Факультет с ID {} был удален", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> sortByColor(String color) {
        logger.info("Факультеты с таким цветом: {}", color);
        return facultyRepository.findByColor(color);
        //return faculties.values().stream()
          //      .filter(faculty -> faculty.getColor().equals(color))
            //    .collect(Collectors.toList());
    }
    public Collection <Faculty> findByNameOrColor (String name, String color) {
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name,color);
    }
    //public Collection<Faculty> findByStudent (Long id) {
        //return facultyRepository.findByStudent_id(id);
    //}
    //
}
