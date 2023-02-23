package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CourseRepositoryTests {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UniversityRepository universityRepository;

    @AfterEach
    public void clearDatabase() {
        courseRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourse() {
        Course course = new Course();
        University university = new University();

        String name = "McGill";
        String codename = "ECSE428";
        String title = "Intro to Software Engineering";
        String description = "Learn how to build software";
        boolean approved = true;

        university.setName(name);
        course.setCodename(codename);
        course.setTitle(title);
        course.setDescription(description);
        course.setApproved(approved);
        course.setUniversity(university);

        university = universityRepository.save(university);
        course = courseRepository.save(course);

        Long courseID = course.getId();
        Long universityID = university.getId();

        course = courseRepository.findById(courseID).orElse(null);
        university = universityRepository.findById(universityID).orElse(null);

        assertNotNull(course);
        assertNotNull(university);

        assertEquals(name, course.getUniversity().getName());
        assertEquals(codename, course.getCodename());
        assertEquals(title, course.getTitle());
        assertEquals(description, course.getDescription());
        assertEquals(approved, course.isApproved());

    }
}