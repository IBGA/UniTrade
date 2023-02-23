package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.CourseRequest;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

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
    CourseRepository courseRepository;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        courseRequestRepository.deleteAll();
        courseRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseRequest() {
        CourseRequest courseRequest = new CourseRequest();
        Course course = new Course();
        Person requester = new Person();
        Person validator = new Person();

        String codename = "ECSE428";
        String requesterName = "Requester";
        String validatorName = "Validator";
        boolean approved = true;

        course.setCodename(codename);
        courseRequest.setCourse(course);
        requester.setUsername(requesterName);
        courseRequest.setRequester(requester);
        validator.setUsername(validatorName);
        courseRequest.setValidator(validator);
        courseRequest.setApproved(approved);

        personRepository.save(requester);
        personRepository.save(validator);
        courseRepository.save(course);
        courseRequestRepository.save(courseRequest);

        Long courseRequestId = courseRequest.getId();

        courseRequest = courseRequestRepository.findById(courseRequestId).orElse(null);

        assertNotNull(courseRequest);
        assertEquals(codename, courseRequest.getCourse().getCodename());
        assertEquals(requesterName, courseRequest.getRequester().getUsername());
        assertEquals(validatorName, courseRequest.getValidator().getUsername());
        assertEquals(approved, courseRequest.isApproved());
    }
}