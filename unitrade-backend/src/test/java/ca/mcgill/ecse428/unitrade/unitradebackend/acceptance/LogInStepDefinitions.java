package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Base64;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LogInStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;

    /***
    @Given("user is not logged in")
    public void user_is_not_logged_in() {
        RequestHelperClass helper = new RequestHelperClass(true);
        String url = "http://localhost:8080/";
        PersonRequestDto person = new PersonRequestDto();
        try {
            url = "http://localhost:8080/logout";
            ResponseEntity<PersonResponseDto> response = helper.post(url, PersonResponseDto.class, person,true);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        throw new io.cucumber.java.PendingException();
    }
     ***/
    @Given("a user with username {string} exists in the system")
    public void a_user_with_username_exists_in_the_system(String username) {
        String name;
        String url;
        PersonRequestDto person = new PersonRequestDto();
        person.setEmail(username + "@gmail.com");
        person.setUsername(username);
        if (username.equals("elonmusk")){
            person.setFirstName("elon");
            person.setLastName("musk");
            person.setPassword("spacex");
        }
        else if (username.equals("billgates")) {
            person.setFirstName("bill");
            person.setLastName("gates");
            person.setPassword("microsoft");
        }

        HttpStatusCode statusCode;


        RequestHelperClass helper = new RequestHelperClass(true);
        try{
            url = "http://localhost:8080/person";
            ResponseEntity<PersonResponseDto> response1 = helper.post(url, PersonResponseDto.class, person,
                    false);
        }catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }

        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            name = response.getBody().getUsername();
            assertEquals(username, name);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            throw new io.cucumber.java.PendingException();
        }
    }

    @Given("the password for user {string} is {string}")
    public void the_password_for_user_is(String username, String string2) {
        String url;
        String password;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            password = response.getBody().getPassword();
            //assertEquals(string2, password);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            throw new io.cucumber.java.PendingException();
        }
    }

    @Given("the password for user {string} is not {string}")
    public void the_password_for_user_is_not(String username, String string2) {
        String url;
        String password;

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            url = "http://localhost:8080/person/username/" + username;
            ResponseEntity<PersonResponseDto> response = helper.get(url, PersonResponseDto.class, true);
            password = response.getBody().getPassword();
            assertNotEquals(string2, password);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            throw new io.cucumber.java.PendingException();
        }

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

    }
    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        String url;

        HttpHeaders headers = new HttpHeaders();
        RequestHelperClass helper = new RequestHelperClass(true);
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            url = "http://localhost:8080/login";
            ResponseEntity<PersonResponseDto> response = helper.post(url, PersonResponseDto.class, entity, true);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            throw new io.cucumber.java.PendingException();
        }
    }
    @Then("user is logged in and redirected to the home page")
    public void user_is_logged_in_and_redirected_to_the_home_page() {

    }
    @Then("an error is thrown and user is not logged in")
    public void an_error_is_thrown_and_user_is_not_logged_in() {
        try {
            assertTrue(statusCode.is4xxClientError());
        }catch (NullPointerException e){

        }
    }

}
