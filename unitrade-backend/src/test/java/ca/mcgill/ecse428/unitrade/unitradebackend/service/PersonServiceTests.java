package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {
    @Mock private UniversityRepository universityRepository;
    @Mock private PersonRepository personRepository;
    @Mock private CourseRepository courseRepository;
    @InjectMocks private PersonService personService;
    
    private static final Long MCGILL_KEY = Long.valueOf(0);
    private static final Long COURSE_KEY = Long.valueOf(1);
    private static final Long PERSON_KEY = Long.valueOf(0);
    private static final String PERSON_EMAIL = "user@email.com";
    private static final String PERSON_USERNAME = "user_name";

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(universityRepository.findById(anyLong()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(MCGILL_KEY)){
                            University university = new University();
                            university.setId(MCGILL_KEY);
                            university.setName("McGill");
                            university.setCity("Montreal");
                            university.setDescription("Go Marlets!");
                            return Optional.of(university);
                        } else {
                            return Optional.empty();
                        }
                    }
                );
        lenient()
                .when(courseRepository.findById(anyLong()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(COURSE_KEY)) {
                            University university = new University();
                            university.setId(MCGILL_KEY);
                            university.setName("McGill");
                            university.setCity("Montreal");
                            university.setDescription("Go Marlets!");

                            Course course = new Course();
                            course.setId(COURSE_KEY);
                            course.setTitle("Software Engineering Practice");
                            course.setCodename("ECSE-428");
                            course.setUniversity(university);
                            course.setDescription("A class");
                            course.setApproved(true);
                            return Optional.of(course);
                        } else {
                            return Optional.empty();
                        }
                    }
                );
        lenient()
                .when(personRepository.findByEmail(anyString()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(PERSON_EMAIL)) {
                            Person person = new Person();
                            person.setId(PERSON_KEY);
                            person.setEmail(PERSON_EMAIL);
                            return person;
                        } else {
                            return null;
                        }
                    }
                );
        lenient()
                .when(personRepository.findByUsername(anyString()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(PERSON_USERNAME)) {
                            Person person = new Person();
                            person.setId(PERSON_KEY);
                            person.setUsername(PERSON_USERNAME);
                            return person;
                        } else {
                            return null;
                        }
                    }
                );
        
        Answer<?> returnParameterAsAnswer =
            (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
        lenient()
            .when(personRepository.save(any(Person.class)))
            .thenAnswer(returnParameterAsAnswer);
    }

    //** Test CreatePerson with complete information */
    @Test
    public void testCreatePersonComplete() {
        String email = "adam@email.com";
        String username = "asimard";
        String firstName = "Adam";
        String lastName = "Simard";
        String password = "my_password";
        String profilePicture = "pic.jpg";
        List<Long> enrolledCourseIds = List.of(COURSE_KEY);
        Long universityId = MCGILL_KEY;

        Person person = personService.createPerson(email, username, firstName, lastName, password, profilePicture, enrolledCourseIds, universityId);

        assertNotNull(person);
        assertEquals(email, person.getEmail());
        assertEquals(username, person.getUsername());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(password, person.getPassword());
        assertEquals(profilePicture, person.getProfilePicture());
        for (int i=0; i<person.getEnrolledCourses().size(); i++) {
            assertEquals(enrolledCourseIds.get(i), person.getEnrolledCourses().get(i).getId());
        }
        assertEquals(universityId, person.getUniversity().getId());
    }

    //** Test CreatePerson with duplicate email and username */
    @Test
    public void testCreatePersonDuplicate() {
        try {
            String email = PERSON_EMAIL;
            String username = PERSON_USERNAME;
            String firstName = "Adam";
            String lastName = "Simard";
            String password = "my_password";
            String profilePicture = "pic.jpg";
            List<Long> enrolledCourseIds = List.of(COURSE_KEY);
            Long universityId = MCGILL_KEY;

            personService.createPerson(email, username, firstName, lastName, password, profilePicture, enrolledCourseIds, universityId);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.CONFLICT, e.getStatus());
        }
    }
}
