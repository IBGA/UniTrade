package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.http.HttpStatusCode;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;

public class RegisterNewUserStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @Given("user is not logged in")
    public void user_is_not_logged_in() {

    }


    @Given("a user with email {string} or username {string} does not already exist in the system")
    public void a_user_with_email_or_username_does_not_already_exist_in_the_system(String email, String username) {
        // Long id;
        // String url;

        // RequestHelperClass helper = new RequestHelperClass(true);

        // try {
        //     url = "http://localhost:8080/person/email/" + email;
        //     ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
        //     id = response.getBody().getId();
        //     url = "http://localhost:8080/person/" + id;
        //     helper.delete(url, true);
        // } catch (HttpClientErrorException e) {
        //     statusCode = e.getStatusCode();
        // }
        // try {
        //     url = "http://localhost:8080/person/username/" + username;
        //     ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
        //     id = response.getBody().getId();
        //     url = "http://localhost:8080/person/" + id;
        //     helper.delete(url, true);
        // } catch (HttpClientErrorException e) {
        //     statusCode = e.getStatusCode();
        // }
    }

    @When("user registers with email {string}, username {string}, first name {string}, last name {string} and password {string}")
    public void user_registers_with_email_and_username(String email, String username, String first_name, String last_name, String password) {
        String url;
        PersonRequestDto person = new PersonRequestDto();
        person.setEmail(email);
        person.setUsername(username);
        person.setPassword(password);
        person.setFirstName(first_name);
        person.setLastName(last_name);

        url = "http://localhost:8080/person";

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<PersonResponseDto> response = helper.post(url, PersonResponseDto.class, person,
                    false);
            statusCode = response.getStatusCode();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("a new user account with email {string}, username {string}, first name {string}, last name {string} and password {string} is created")
    public void a_new_user_account_with_email_and_username_is_created(String email, String username, String first_name, String last_name, String password) {
        String url;

        assertEquals(HttpStatus.CREATED, statusCode);

        url = "http://localhost:8080/person/exists/" + email;

        RequestHelperClass helper = new RequestHelperClass(false);

        ResponseEntity<Boolean> response = helper.get(url, Boolean.class, false);
        assertTrue(response.getBody());
    }

    @Given("a user with email {string} or username {string} already exists in the system")
    public void a_user_with_email_or_username_already_exists_in_the_system(String email, String username) {
        String url;
        RequestHelperClass helper = new RequestHelperClass(true);
        try{
            url = "http://localhost:8080/person/email/" + email;
            helper.get(url, PersonResponseDto.class, true);
            url = "http://localhost:8080/person/username/" + username;
            helper.get(url, PersonResponseDto.class, true);

        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();

            PersonRequestDto person = new PersonRequestDto();
            person.setEmail(email);
            person.setUsername(username);
            person.setPassword("password");
            person.setFirstName("first");
            person.setLastName("last");

            url = "http://localhost:8080/person";
            helper.post(url, PersonResponseDto.class,  person, true);
        }
    }

    @Then("an error is thrown to create a new user account with email {string}, username {string}, first name {string}, last name {string} and password {string}")
    public void an_error_message_is_thrown(String email, String username, String firstname, String lastname, String password) {
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
When user registers with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>"
Then a new user account with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>" is created

Examples: 
| email             | username      | first_name    | last_name     | password      |
| zidane@zidane.com | zidane        | Zinedine      | Zidane        | zidane        |
| amidon@gmail.com  | amidon        | Andre         | Agassi        | amidon        |

Scenario Outline: User email or username already exist in system. (Error Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" already exists in the system
When user registers with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>"
Then a new user account with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>" is not created

Examples:
| email               | username      | first_name    | last_name     | password      |
| etienne@hotmail.com | etienne       | Etienne       | Capoue        | etienne       |
| dean35@outlook.com  | dean35        | Dean          | Henderson     | dean35        |
*/