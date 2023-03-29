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
    @And("a university with name {string} and city {string} exists in the system")
    public void a_university_with_name_and_city_exists_in_the_system(String name, String city){
        String url = "http://localhost:8080/university" + city + "/" + name;
        RequestHelperClass helper = new RequestHelperClass(true);

        try{
            ResponseEntity<UniversityResponseDto> response = helper.get(url, UniversityResponseDto.class, false);
            UniversityResponseDto university = response.getBody();
            assertNotNull(university);
            if (response.getStatusCode().is2xxSuccessful()) {
                url = "http://localhost:8080/university/" + university.getId();
                helper.delete(url, false);
            }
        } catch (HttpClientErrorException e) {

        }
        
    }

    @And("user is a moderator for univeristy with name {string} and city {string}")
    public void user_is_a_moderator_for_university_with_name_and_city(String name, String city){
        
    }

    @And("a course for university {string} and codename {string} does not already exist in the system")
    public void a_course_for_university_with_codename_does_not_already_exist_in_the_system(String universityName, String courseCodeName){
        String url = "http://localhost:8080/university/" + universityName;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<CourseResponseDto> response = helper.get(url, CourseResponseDto.class, false);
            CourseResponseDto course = response.getBody();
            assertNotNull(course);
            if (response.getStatusCode().is2xxSuccessful()) {
                url = "http://localhost:8080/university/" + course.getUniversity().getId();
                helper.delete(url, false);
            }
        } catch (HttpClientErrorException e) {

        }
    }

    @When("user attempts to create a course for university {string} with title {string}, codename {string} and description {string}")
    public void user_attempts_to_create_a_course_for_university_with_title_codename_and_description(String universityName, String title, String courseCodeName, String description){
        RestTemplate restTemplate = new RestTemplate();
        Long universityId = null;

        String url = "http://localhost:8080/university/" + universityName; //check if city needed
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

        url = "http://localhost:8080/university/" + universityName;

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

    @Then("a new course for university {string} with title {string}, codename {string}, and description {string} is added to the system")
    public void a_new_course_for_university_with_title_codename_and_description_is_added_to_the_system(String universityName, String title, 
                String codeName, String description){
       String url = "http://localhost:8080/university/" + universityName;
       RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<CourseResponseDto> response = helper.get(url, CourseResponseDto.class, true);
            CourseResponseDto course = response.getBody();
            assertNotNull(course);
            assertEquals(universityName, course.getUniversity().getName());
            assertEquals(title, course.getTitle());
            assertEquals(codeName, course.getCodename());
            assertEquals(description, course.getDescription());
        } catch (HttpClientErrorException e) {
            assertTrue(false, "Failed to get course: " + e.getMessage());
        }
    }

    @Then("a new course for university {string} with title {string}, codename {string}, and description {string} is not added to the system")
    public void a_new_course_for_university_with_title_codename_and_description_is__not_added_to_the_system(String universityName, String title, 
                String codeName, String description){
                    String url = "http://localhost:8080/university/" ;

                    RequestHelperClass helper = new RequestHelperClass(true);
            
                    try {
                        helper.get(url, CourseResponseDto.class, true);
                    } catch (HttpClientErrorException e) {
                        assertEquals(404, e.getStatusCode().value());
                    }

                }
}

