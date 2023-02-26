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
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UniversityServiceTests {
    @Mock private UniversityRepository universityRepository;
    @InjectMocks private UniversityService universityService;

    private static final Long MCGILL_KEY = Long.valueOf(0);
    private static final Long CONCORDIA_KEY = Long.valueOf(1);
    private static final String MONTREAL_CITY = "Montreal";
    private static final String MCGILL_NAME = "McGill";
    private static final String CONCORDIA_NAME = "Concordia";

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(universityRepository.findById(anyLong()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(MCGILL_KEY)){
                            University university = new University();
                            university.setId(MCGILL_KEY);
                            university.setName(MCGILL_NAME);
                            university.setCity(MONTREAL_CITY);
                            university.setDescription("Go Marlets!");
                            return Optional.of(university);
                        } else if (invocation.getArgument(0).equals(CONCORDIA_KEY)) {
                            University university = new University();
                            university.setId(CONCORDIA_KEY);
                            university.setName(CONCORDIA_NAME);
                            university.setCity(MONTREAL_CITY);
                            university.setDescription("Go Stingers!");
                            return university;
                        } else {
                            return Optional.empty();
                        }
                    }
                );
        lenient()
                .when(universityRepository.findByNameAndCity(anyString(), anyString()))
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(MCGILL_NAME) && invocation.getArgument(1).equals(MONTREAL_CITY)) {
                            University university = new University();
                            university.setId(MCGILL_KEY);
                            university.setName(MCGILL_NAME);
                            university.setCity(MONTREAL_CITY);
                            university.setDescription("Go Marlets!");
                            return university;
                        } else if (invocation.getArgument(0).equals(CONCORDIA_NAME) && invocation.getArgument(1).equals(MONTREAL_CITY)) {
                            University university = new University();
                            university.setId(CONCORDIA_KEY);
                            university.setName(CONCORDIA_NAME);
                            university.setCity(MONTREAL_CITY);
                            university.setDescription("Go Stingers!");
                            return university;
                        } else {
                            return null;
                        }
                    }
                );
        lenient()
                .when(universityRepository.findAll())
                .thenAnswer(
                    (InvocationOnMock invocation) -> {
                        University mcgill = new University();
                        mcgill.setId(MCGILL_KEY);
                        mcgill.setName(MCGILL_NAME);
                        mcgill.setCity(MONTREAL_CITY);
                        mcgill.setDescription("Go Marlets!");
                        University concordia = new University();
                        concordia.setId(CONCORDIA_KEY);
                        concordia.setName(CONCORDIA_NAME);
                        concordia.setCity(MONTREAL_CITY);
                        concordia.setDescription("Go Stingers!");
                        return List.of(mcgill, concordia);
                    }
                );
        Answer<?> returnParameterAsAnswer =
            (InvocationOnMock invocation) -> {
                return invocation.getArgument(0);
            };
        lenient()
            .when(universityRepository.save(any(University.class)))
            .thenAnswer(returnParameterAsAnswer);
    }

    //** Test CreateUniversity with complete information, dupe city */
    @Test
    public void testCreateUniversityComplete() {
        String name = "UQAM";
        String city = "Montreal";
        String description = "Go Cheval Bleu!";

        University university = universityService.createUniversity(name, city, description);

        assertNotNull(university);
        assertEquals(name, university.getName());
        assertEquals(city, university.getCity());
        assertEquals(description, university.getDescription());
    }

    //** Test CreateUniversity with complete information, dupe name and city */
    @Test
    public void testCreateUniversityDupNameAndCity() {
        try {
            String name = "McGill";
            String city = "Montreal";
            String description = "Go Marlets!";

            universityService.createUniversity(name, city, description);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.CONFLICT, e.getStatus());
        }
    }
}
