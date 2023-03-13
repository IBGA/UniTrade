package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.Base64;

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
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;

public class AddUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @Given("user is logged in")
    public void user_is_logged_in() {
        RestTemplate restTemplate = new RestTemplate();

        PersonRequestDto body = new PersonRequestDto();
        body.setEmail("testEmail");
        body.setUsername("testUsername");
        body.setPassword("testPassword");
        body.setFirstName("testFirstName");
        body.setLastName("testLastName");

        try {
            String url = "http://localhost:8080/person";
            restTemplate.postForEntity(
                    url,
                    body,
                    PersonResponseDto.class);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @And("a university with name {string} and city {string} does not already exist in the system")
    public void a_university_with_name_and_city_does_not_already_exist_in_the_system(String name, String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/university/" + city + "/" + name;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.exchange(url, HttpMethod.GET, entity,
                    UniversityResponseDto.class);
            if (university.getStatusCode().is2xxSuccessful()) {
                url = "http://localhost:8080/university/" + university.getBody().getId();
                restTemplate.delete(url);
            }
        } catch (HttpClientErrorException e) {
            // Do nothing
        }
    }

    @When("user attempts to create a university with name {string}, city {string}, and description {string}")
    public void user_attempts_to_create_a_university_with_name_city_and_description(String name, String city,
            String description) {
        RestTemplate restTemplate = new RestTemplate();

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription(description);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<UniversityRequestDto> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<UniversityResponseDto> response = restTemplate.exchange("http://localhost:8080/university",
                    HttpMethod.POST, entity, UniversityResponseDto.class);
            statusCode = response.getStatusCode();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("a new university with name {string}, city {string}, and description {string} is added to the system")
    public void the_new_university_with_name_city_and_description_is_added_to_the_system(String name, String city,
            String description) {
        String url = "http://localhost:8080/university/" + city + "/" + name;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.exchange(url, HttpMethod.GET, entity,
                    UniversityResponseDto.class);
            assertNotNull(university);
            assertEquals(name, university.getBody().getName());
            assertEquals(city, university.getBody().getCity());
            assertEquals(description, university.getBody().getDescription());
        } catch (HttpClientErrorException e) {
            assertTrue(false, "Failed to get university: " + e.getMessage());
        }
    }

    @Given("a university with name {string} and city {string} already exists in the system")
    public void a_university_with_name_and_city_already_exists_in_the_system(String name, String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription("A university");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UniversityRequestDto> entity = new HttpEntity<>(body, headers);

        String url = "http://localhost:8080/university";

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.exchange(url, HttpMethod.POST, entity,UniversityResponseDto.class);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("an error is thrown")
    public void an_error_is_thrown() {
        // check status
        assertTrue(statusCode.is4xxClientError());
    }

    @And("a new university with name {string}, city {string}, and description {string} is not added to the system")
    public void a_new_university_with_name_city_and_description_is_not_added_to_the_system(String name, String city,
            String description) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/university/" + city + "/" + name;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.exchange(url, HttpMethod.GET, entity,
                    UniversityResponseDto.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}
