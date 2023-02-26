package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import ca.mcgill.ecse428.unitrade.unitradebackend.controller.UniversityRestController;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;


public class AddUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @Given("user is logged in")
    public void user_is_logged_in() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        
    }

    @And("a university with name {string} and city {string} does not already exist in the system")
    public void a_university_with_name_and_city_does_not_already_exist_in_the_system(String name, String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/university/" + city + "/" + name;

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.getForEntity(url, UniversityResponseDto.class);
            if (university.getStatusCode().is2xxSuccessful()) {
                url = "http://localhost:8080/university/" + university.getBody().getId();
                restTemplate.delete(url);
            }
        } catch (Exception e) {
            //do nothing
        }
        
    }

    @When("user attempts to create a university with name {string}, city {string}, and description {string}")
    public void user_attempts_to_create_a_university_with_name_city_and_description(String name, String city, String description) {

        RestTemplate restTemplate = new RestTemplate();

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription(description);

        try {
            ResponseEntity<UniversityResponseDto> response = restTemplate.postForEntity("http://localhost:8080/university", body, UniversityResponseDto.class);
            statusCode = response.getStatusCode();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }

    }

    @Then("a new university with name {string}, city {string}, and description {string} is added to the system")
    public void the_new_university_with_name_city_and_description_is_added_to_the_system(String name, String city, String description) {
        String url = "http://localhost:8080/university/" + city + "/" + name;

        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<UniversityResponseDto> university = restTemplate.getForEntity(url, UniversityResponseDto.class);
        assertNotNull(university);
        assertEquals(name, university.getBody().getName());
        assertEquals(city, university.getBody().getCity());
        assertEquals(description, university.getBody().getDescription());

    }

    @Given ("a university with name {string} and city {string} already exists in the system")
    public void a_university_with_name_and_city_already_exists_in_the_system(String name, String city) {
        RestTemplate restTemplate = new RestTemplate();

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription("A university");
        
        try {
            restTemplate.postForEntity("http://localhost:8080/university", body, UniversityResponseDto.class);
        } catch (Exception e) {
            statusCode = HttpStatus.BAD_REQUEST;
        }

    }

    @Then("an error is thrown")
    public void an_error_is_thrown() {
        //check status
        assertTrue(statusCode.is4xxClientError());
    }

    @And("a new university with name {string}, city {string}, and description {string} is not added to the system")
    public void a_new_university_with_name_city_and_description_is_not_added_to_the_system(String name, String city, String description) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/university/" + city + "/" + name;

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.getForEntity(url, UniversityResponseDto.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }

    }

}
