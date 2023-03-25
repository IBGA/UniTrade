package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.http.HttpStatusCode;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;

public class AddUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @Given("user is logged in")
    public void user_is_logged_in() {

    }

    @And("a university with name {string} and city {string} does not already exist in the system")
    public void a_university_with_name_and_city_does_not_already_exist_in_the_system(String name, String city) {
        String url = "http://localhost:8080/university/" + city + "/" + name;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
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

    @When("user attempts to create a university with name {string}, city {string}, and description {string}")
    public void user_attempts_to_create_a_university_with_name_city_and_description(String name, String city,
            String description) {

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription(description);

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<UniversityResponseDto> response = helper.post("http://localhost:8080/university",
                    UniversityResponseDto.class, body, true);
            statusCode = response.getStatusCode();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("a new university with name {string}, city {string}, and description {string} is added to the system")
    public void the_new_university_with_name_city_and_description_is_added_to_the_system(String name, String city,
            String description) {

        String url = "http://localhost:8080/university/" + city + "/" + name;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<UniversityResponseDto> response = helper.get(url, UniversityResponseDto.class, true);
            UniversityResponseDto university = response.getBody();
            assertNotNull(university);
            assertEquals(name, university.getName());
            assertEquals(city, university.getCity());
            assertEquals(description, university.getDescription());
        } catch (HttpClientErrorException e) {
            assertTrue(false, "Failed to get university: " + e.getMessage());
        }
    }

    @Given("a university with name {string} and city {string} already exists in the system")
    public void a_university_with_name_and_city_already_exists_in_the_system(String name, String city) {

        UniversityRequestDto body = new UniversityRequestDto();
        body.setName(name);
        body.setCity(city);
        body.setDescription("A university");

        String url = "http://localhost:8080/university";

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            helper.post(url, UniversityResponseDto.class, body, true);
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

        String url = "http://localhost:8080/university/" + city + "/" + name;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            helper.get(url, UniversityResponseDto.class, true);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getStatusCode().value());
        }
    }
}
