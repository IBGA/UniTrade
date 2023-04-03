package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.CourseRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

public class CreateCourseStepDefinitions extends AcceptanceTest{
    
    HttpStatusCode statusCode;

    @And("user is a moderator for university")
    public void user_is_a_moderator_for_university(){

    }

    @When("user attempts to create a course for university with name {string} and city {string} with title {string}, codename {string}, and description {string}")
    public void user_attempts_to_create_a_course_for_university_with_title_codename_and_description(String universityName, String universityCity, String title, String courseCodeName, String description){
        RestTemplate restTemplate = new RestTemplate();
        Long universityId = null;

        String url = "http://localhost:8080/university/" + universityCity + "/" + universityName; //check if city needed
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                 "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<UniversityResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    UniversityResponseDto.class);
            universityId = response.getBody().getId();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            if(statusCode.value() == 404) return;
        }

        url = "http://localhost:8080/course";

        CourseRequestDto body = new CourseRequestDto();
            body.setCodename(courseCodeName);
            body.setUniversityId(universityId);
            body.setDescription(description);
            body.setTitle(title);

        HttpEntity<CourseRequestDto> courseRequestEntity = new HttpEntity<CourseRequestDto>(body, headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, courseRequestEntity, CourseResponseDto.class);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("a new course for university with name {string} and city {string} with title {string}, codename {string}, and description {string} is added to the system")
    public void a_new_course_for_university_with_title_codename_and_description_is_added_to_the_system(String universityName, String universityCity, String title, 
                String codeName, String description){
       String url = "http://localhost:8080/university/" + universityCity + "/" + universityName;
       RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<UniversityResponseDto> uniresponse = helper.get(url, UniversityResponseDto.class, true);
            UniversityResponseDto university = uniresponse.getBody();

            url = "http://localhost:8080/course/codename/"+codeName;
            ResponseEntity<CourseResponseDto> courseresponse = helper.get(url, CourseResponseDto.class, true);
            CourseResponseDto course = courseresponse.getBody();
            assertNotNull(course);
            assertNotNull(university);
            assertEquals(course.getUniversity().getId(), university.getId());
            assertEquals(universityName, course.getUniversity().getName());
            assertEquals(title, course.getTitle());
            assertEquals(codeName, course.getCodename());
            assertEquals(description, course.getDescription());
        } catch (HttpClientErrorException e) {
            assertTrue(false, "Failed to get course: " + e.getMessage());
        }
    }

    @Then("a new course for university with name {string} and city {string} with title {string}, codename {string}, and description {string} is not added to the system")
    public void a_new_course_for_university_with_title_codename_and_description_is__not_added_to_the_system(String universityName, String universityCity, String title, 
                String codeName, String description){
                    String url = "http://localhost:8080/course/" ;

                    RequestHelperClass helper = new RequestHelperClass(true);
            
                    try {
                        helper.get(url+codeName, CourseResponseDto.class, true);
                    } catch (HttpClientErrorException e) {
                        assertEquals(true, e.getStatusCode().is4xxClientError());
                    }

                }
    @Then("an error is thrown")
    public void the_item_posting_is_not_created_and_an_error_is_thrown() {
        assertEquals(true, statusCode.is4xxClientError());
    }
}

