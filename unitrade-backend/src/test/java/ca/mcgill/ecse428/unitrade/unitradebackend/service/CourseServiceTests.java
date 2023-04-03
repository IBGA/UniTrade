package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {
    @Mock private UniversityRepository universityRepository;
    @Mock private CourseRepository courseRepository;
    @InjectMocks private CourseService courseService;

    private static final Long MCGILL_KEY = Long.valueOf(0);
    private static final Long COURSE_KEY = Long.valueOf(0);

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(universityRepository.findById(anyLong()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(MCGILL_KEY)){
                            University university = new University();
                            university.setId(MCGILL_KEY);
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
        Answer<?> returnParameterAsAnswer =
            (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
        lenient()
            .when(courseRepository.save(any(Course.class)))
            .thenAnswer(returnParameterAsAnswer);
    }

    //** Test CreateCourse with complete information */
    @Test
    public void testCreateCourseComplete() {
        String title = "Thermo of Computing";
        String codename = "ECSE-310";
        String description = "A class";
        Long universityId = MCGILL_KEY;

        Course course = courseService.createCourse(title, codename, description, universityId);

        assertNotNull(course);
        assertEquals(title, course.getTitle());
        assertEquals(codename, course.getCodename());
        assertEquals(description, course.getDescription());
        assertEquals(universityId, course.getUniversity().getId());
    }

    //** Test CreateCourse with non-existant university */
    @Test
    public void testCreateCourseNoUniversity() {
        try {
            String title = "Thermo of Computing";
            String codename = "ECSE-310";
            String description = "A class";
            Long universityId = Long.valueOf(9999);

            courseService.createCourse(title, codename, description, universityId);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
        
    }
}
