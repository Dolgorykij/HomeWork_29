package ru.hogwarts.school_HomeWork_29;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school_HomeWork_29.model.Faculty;
import ru.hogwarts.school_HomeWork_29.service.FacultyService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyServiceTest {
    private FacultyService facultyService;

   // @BeforeEach
    // public void setUp() {
    //    facultyService = new FacultyService();
    // }

    // @Test
    // public void testAddFaculty() {
    //   Faculty faculty = new Faculty(0L,"Slizer","Green");
    //  Faculty addedFaculty = facultyService.addFaculty(faculty);
    // assertNotNull(addedFaculty.getId());
    // assertEquals("Slizer", addedFaculty.getName());
    //assertEquals("Green", addedFaculty.getColor());
    // }

// @Test
//public void testFindFaculty() {
//    Faculty faculty = new Faculty(0L,"Slizer","Green");
// Faculty addedFaculty = facultyService.addFaculty(faculty);
// Faculty foundFaculty = facultyService.findFaculty(addedFaculty.getId());
// assertEquals(addedFaculty.getId(), foundFaculty.getId());
// assertEquals("Slizer", foundFaculty.getName());
//assertEquals("Green", foundFaculty.getColor());
//}

//@Test
//public void testEditFaculty() {
//   Faculty faculty = new Faculty(0L,"Slizer","Green");
//  Faculty addedFaculty = facultyService.addFaculty(faculty);

// addedFaculty.setName("Puffenduj");
// addedFaculty.setColor("Yellow");
//Faculty editedFaculty = facultyService.editFaculty(addedFaculty);
// assertEquals("Puffenduj", editedFaculty.getName());
// assertEquals("Yellow", editedFaculty.getColor());
// assertEquals(addedFaculty.getId(), editedFaculty.getId());
// }

// @Test
//public void testDeleteFaculty() {
//    Faculty faculty = new Faculty(0L,"Slizer","Green");
//    Faculty addedFaculty = facultyService.addFaculty(faculty);
//    Faculty deletedFaculty = facultyService.deleteFaculty(addedFaculty.getId());
//   assertEquals("Slizer", deletedFaculty.getName());
//   assertEquals("Green", deletedFaculty.getColor());
//   assertNull(facultyService.findFaculty(addedFaculty.getId()));
// }

//@Test
// public void testSortByColor() {
//    Faculty faculty1 = new Faculty(0L,"Slizer","Green");
//   Faculty faculty2 = new Faculty(0L,"Griffin","Red");
//   Faculty faculty3 = new Faculty(0L,"Puffen","Yellow");
//   facultyService.addFaculty(faculty1);
//  facultyService.addFaculty(faculty2);
//   facultyService.addFaculty(faculty3);

// List<Faculty> facultiesByColor = facultyService.sortByColor("Green");

// assertEquals(1, facultiesByColor.size());
// assertTrue(facultiesByColor.stream().anyMatch(faculty -> faculty.getName().equals("Slizer")));
//}
}
