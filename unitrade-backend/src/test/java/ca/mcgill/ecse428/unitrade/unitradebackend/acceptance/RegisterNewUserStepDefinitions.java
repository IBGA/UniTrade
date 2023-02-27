package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

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

import ca.mcgill.ecse428.unitrade.unitradebackend.controller.ItemPostingRestController;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;

import ca.mcgill.ecse428.unitrade.unitradebackend.controller.PersonRestController;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;

public class RegisterNewUserStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @Given("user is not logged in")
    public void user_is_not_logged_in() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @Given("a user with email {string} or username {string} does not already exist in the system")
    public void a_user_with_email_or_username_does_not_already_exist_in_the_system(String email, String username) {
        RestTemplate restTemplate = new RestTemplate();
        Long id;
        String url;
        
        try {        
            url = "http://localhost:8080/person/email/" + email;
            ResponseEntity<PersonResponseDto> response = restTemplate.getForEntity(url, PersonResponseDto.class);
            id = response.getBody().getId();
            url = "http://localhost:8080/person/" + id;
            restTemplate.delete(url);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = restTemplate.getForEntity(url, PersonResponseDto.class);
            id = response.getBody().getId();
            url = "http://localhost:8080/person/" + id;
            restTemplate.delete(url);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @When("user registers with email {string} and username {string}")
    public void user_registers_with_email_and_username(String email, String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url;
        
        String name = "McGill University";
        String city = "Montreal";
        Long universityId;

        try {
            url = "http://localhost:8080/university/" + city + "/" + name;
            ResponseEntity<UniversityResponseDto> response = restTemplate.getForEntity(url, UniversityResponseDto.class);
            universityId = response.getBody().getId();

        } catch (Exception exception) {
            
            UniversityRequestDto body = new UniversityRequestDto();
            body.setName(name);
            body.setCity(city);
            body.setDescription("description");

            url = "http://localhost:8080/university";
            ResponseEntity<UniversityResponseDto> response = restTemplate.postForEntity(url, body, UniversityResponseDto.class);
            universityId = response.getBody().getId();
        }

        PersonRequestDto person = new PersonRequestDto();
        person.setEmail(email);
        person.setUsername(username);
        person.setPassword("password");
        person.setFirstName("first");
        person.setLastName("last");
        person.setEnrolledCourseIds(new ArrayList<>());
        person.setUniversityId(universityId);

        url = "http://localhost:8080/person";

        try {
            ResponseEntity<PersonResponseDto> response = restTemplate.postForEntity(url, person, PersonResponseDto.class);
            statusCode = response.getStatusCode();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("a new user account with email {string} and username {string} is created")
    public void a_new_user_account_with_email_and_username_is_created(String email, String username) {
        RestTemplate restTemplate = new RestTemplate();
        Long id;
        String url;

        assertEquals(HttpStatus.CREATED, statusCode);
        
        url = "http://localhost:8080/person/email/" + email;
        ResponseEntity<PersonResponseDto> response = restTemplate.getForEntity(url, PersonResponseDto.class);
        
        assertEquals(username, response.getBody().getUsername());
        
    }

    @Given("a user with email {string} or username {string} already exists in the system")
    public void a_user_with_email_or_username_already_exists_in_the_system(String email, String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url;
        
        try {        
            url = "http://localhost:8080/person/email/" + email;
            restTemplate.getForEntity(url, PersonResponseDto.class);
            url = "http://localhost:8080/person/username/" + username;
            restTemplate.getForEntity(url, PersonResponseDto.class);

        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            String name = "McGill University";
            String city = "Montreal";
            Long universityId;

            try {
                url = "http://localhost:8080/university/" + city + "/" + name;
                ResponseEntity<UniversityResponseDto> response = restTemplate.getForEntity(url, UniversityResponseDto.class);
                universityId = response.getBody().getId();

            } catch (Exception exception) {
                
                UniversityRequestDto body = new UniversityRequestDto();
                body.setName(name);
                body.setCity(city);
                body.setDescription("description");

                url = "http://localhost:8080/university";
                ResponseEntity<UniversityResponseDto> response = restTemplate.postForEntity(url, body, UniversityResponseDto.class);
                universityId = response.getBody().getId();
            }

            PersonRequestDto person = new PersonRequestDto();
            person.setEmail(email);
            person.setUsername(username);
            person.setPassword("password");
            person.setFirstName("first");
            person.setLastName("last");
            person.setEnrolledCourseIds(new ArrayList<>());
            person.setUniversityId(universityId);

            url = "http://localhost:8080/person";
            restTemplate.postForEntity(url, person, PersonResponseDto.class);
        }
    }

    @Then("an error message is thrown and no new user account is created")
    public void an_error_message_is_thrown() {
        assertTrue(statusCode.is4xxClientError());
    }




}
    

/*
Feature: Register new user

As a UniTrade potential user
I want to register an account
So that I can trade in the online marketplace

Scenario Outline: User email and username does not already exist in system. (Normal Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" does not already exist in the system
When user registers with email "<email>" and username "<username>"
Then a new user account with email "<email>" and username "<username>" is created

Examples:
| email                  | username          |
| bob@gmail.com          | b.o.b.            |
| samson@hotmail.com     | samsonmamson      |


Scenario Outline: User email or username already exists in system. (Error Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" already exists in the system
When user registers with email "<email>" and username "<username>"
Then an error is thrown

Examples:
| email                  | username          |
| bob@gmail.com          | b.o.b.            |
| samson@hotmail.com     | samsonmamson      |
*/