package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.ItemPostingRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ItemPostingServiceTests {
    @Mock private UniversityRepository universityRepository;
    @Mock private ItemPostingRepository itemPostingRepository;
    @Mock private PersonRepository personRepository;
    @Mock private CourseRepository courseRepository;
    @InjectMocks private ItemPostingService itemPostingService;

    private static final Long MCGILL_KEY = Long.valueOf(0);
    private static final Long COURSE_KEY = Long.valueOf(1);
    private static final Long ITEM_KEY = Long.valueOf(0);
    private static final Long POSTER_KEY = Long.valueOf(0);
    private static final Long BUYER_KEY = Long.valueOf(1);

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
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(BUYER_KEY)) {
                            Person person = new Person();
                            person.setId(BUYER_KEY);
                            return Optional.of(person);
                        } else if (invocation.getArgument(0).equals(POSTER_KEY)) {
                            Person person = new Person();
                            person.setId(POSTER_KEY);
                            return Optional.of(person);
                        } else {
                            return Optional.empty();
                        }
                    }
                );
        lenient()
                .when(itemPostingRepository.findByUniversityAndCourses(any(University.class), any(Course.class)))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (((University)invocation.getArgument(0)).getId().equals(MCGILL_KEY) && ((Course)invocation.getArgument(1)).getId().equals(COURSE_KEY)) {
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

                            ItemPosting itemPosting = new ItemPosting();
                            itemPosting.setId(ITEM_KEY);
                            itemPosting.setCourses(List.of(course));
                            itemPosting.setUniversity(university);
                            return List.of(itemPosting);
                        } else {
                            return Optional.empty();
                        }
                    }
                );
        lenient()
                .when(itemPostingRepository.findAll())
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        ItemPosting itemPosting = new ItemPosting();
                        itemPosting.setId(ITEM_KEY);
                        return List.of(itemPosting);
                    }
                );
        Answer<?> returnParameterAsAnswer =
            (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
        lenient()
            .when(itemPostingRepository.save(any(ItemPosting.class)))
            .thenAnswer(returnParameterAsAnswer);
    }

    //** Test CreateItemPosting with complete information */
    @Test
    public void testCreateItemPostingComplete() {
        String title = "Thermo of Computing Book";
        String description = "A class textbook";
        String image = "imgur.com";
        Date datePosted = new Date(0);
        Long universityId = MCGILL_KEY;
        Long posterId = POSTER_KEY;
        List<Long> courseIds = List.of(COURSE_KEY);
        boolean isAvailable = false;
        Double price = 10.0;
        Long buyerId = BUYER_KEY;
    

        ItemPosting itemPosting = itemPostingService.createItemPosting(title, description, image, datePosted, universityId, posterId, courseIds, false, price, buyerId);

        assertNotNull(itemPosting);
        assertEquals(title, itemPosting.getTitle());
        assertEquals(description, itemPosting.getDescription());
        assertEquals(datePosted, itemPosting.getDatePosted());
        assertEquals(universityId, itemPosting.getUniversity().getId());
        assertEquals(posterId, itemPosting.getPoster().getId());
        for (int i=0; i<itemPosting.getCourses().size(); i++) {
            assertEquals(courseIds.get(i), itemPosting.getCourses().get(i).getId());
        }
        assertEquals(isAvailable, itemPosting.isAvailable());
        assertEquals(price, itemPosting.getPrice());
        assertEquals(buyerId, itemPosting.getBuyer().getId());
    }

    //** Test GetItemPostingByUniversityAndCourse */
    @Test
    public void testGetItemPostingByUniversityAndCourse() {
        Long universityId = MCGILL_KEY;
        Long courseId = COURSE_KEY;
        
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostingsByUniversityAndCourse(universityId, courseId);

        assertNotNull(itemPostings);
        assertEquals(universityId, itemPostings.get(0).getUniversity().getId());
        assertEquals(courseId, itemPostings.get(0).getCourses().get(0).getId());
    }

    //** Test GetAllITemPostings */
    @Test
    public void testGetAllItemPostings() {
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostings();

        assertNotNull(itemPostings);
        assertEquals(1, itemPostings.size());
        assertEquals(ITEM_KEY, itemPostings.get(0).getId());
    }
}
