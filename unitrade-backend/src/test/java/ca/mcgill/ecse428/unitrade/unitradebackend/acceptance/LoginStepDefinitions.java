package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;
import io.cucumber.java.en.*;

import java.util.Base64;
import java.util.ArrayList;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpStatusCode;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;
public class LoginStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;
    @Given("user is not logged in")
    public void user_is_not_logged_in() {
        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            url = "http://localhost:8080/logout";
            ResponseEntity<PersonResponseDto> response = helper.post(url, PersonResponseDto.class, true);
            id = response.getBody().getId();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        throw new io.cucumber.java.PendingException();
    }

    @Given("a user with username {string} exists in the system")
    public void a_user_with_username_exists_in_the_system(String string) {
        Long id;
        String url;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        throw new io.cucumber.java.PendingException();
    }

    @Given("the password for user {string} is {string}")
    public void the_password_for_user_is(String username, String string2) {
        Long id;
        String url;
        String password;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            id = response.getBody().getId();
            password = response.getBody().getPassword();
            assertEquals(string2,password)
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        RequestHelperClass helper = new RequestHelperClass(true);
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            url = "http://localhost:8080/login";
            ResponseEntity<PersonResponseDto> response = helper.post(url, PersonResponseDto.class, entity, true);
            id = response.getBody().getId();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("user is logged in and redirected to the home page")
    public void user_is_logged_in_and_redirected_to_the_home_page() {

        throw new io.cucumber.java.PendingException();
    }

    @Given("the password for user {string} is not {string}")
    public void the_password_for_user_is_not(String username, String string2) {
        Long id;
        String url;
        String password;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            id = response.getBody().getId();
            password = response.getBody().getPassword();
            assertNotEquals(string2, password)
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        throw new io.cucumber.java.PendingException();
        throw new io.cucumber.java.PendingException();
    }

    @Then("an error is thrown and user is not logged in")
    public void an_error_is_thrown_and_user_is_not_logged_in() {
        assertTrue(statusCode.is4xxClientError());
    }

    @Given("a user with username {string} does not in the system")
    public void a_user_with_username_does_not_in_the_system(String username) {
        Long id;
        String url;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            id = response.getBody().getId();
            url = "http://localhost:8080/person/" + id;
            helper.delete(url, true);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        throw new io.cucumber.java.PendingException();
    }
}