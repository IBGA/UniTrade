package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.CourseRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CourseRequestRepositoryTests {

    @Autowired
    CourseRequestRepository courseRequestRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        courseRequestRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseRequest() {
        CourseRequest courseRequest = new CourseRequest();
         
    }
}