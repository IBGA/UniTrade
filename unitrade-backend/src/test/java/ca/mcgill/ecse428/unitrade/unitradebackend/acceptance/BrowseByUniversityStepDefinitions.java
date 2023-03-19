package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.ArrayList;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;

import org.springframework.web.client.HttpClientErrorException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;

public class BrowseByUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @And ("there are item postings for university with name {string} and city {string} in the system")
    public void there_are_item_postings_for_university_with_name_and_city_in_the_system(String name, String city) {
        String url = "http://localhost:8080/university/" + city + "/" + name;
        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            ResponseEntity<UniversityResponseDto> response = helper.get(url, UniversityResponseDto.class, true);
            if (response.getStatusCode().is2xxSuccessful()) {
                Long id = response.getBody().getId();

                url = "http://localhost:8080/itemposting/university/" + id;

                try {
                    ResponseEntity<ItemPostingResponseDto[]> itemPostings = helper.get(url, ItemPostingResponseDto[].class, true);
                    statusCode = itemPostings.getStatusCode();

                } catch (HttpClientErrorException e) {

                    Long posterID = 1L;

                    try {
                        url = "http://localhost:8080/person/email/browsebyuniversity";
                        ResponseEntity<PersonResponseDto> browseResponse = helper.get(url, PersonResponseDto.class, true);
                        posterID = browseResponse.getBody().getId();
                    } catch (HttpClientErrorException e1) {
                        // create person
                        PersonRequestDto person = new PersonRequestDto();
                        person.setUsername("browserbyuniversity");
                        person.setFirstName("Joehn");
                        person.setLastName("Deoe");
                        person.setEmail("browsebyuniversity");
                        person.setPassword("browsebyuniversity");
                        person.setEnrolledCourseIds(new ArrayList<>());
                        person.setUniversityId(id);

                        url = "http://localhost:8080/person";
                        ResponseEntity<PersonResponseDto> personResponse = helper.post(url, PersonResponseDto.class, person, true);
                        // posterID = personResponse.getBody().getId(); Uncommented this and the line 5 lines below because itemPostingRequest doesn't take poster Ids anymore
                    }

                    ItemPostingRequestDto itemPosting = new ItemPostingRequestDto();
                    itemPosting.setTitle("test");
                    itemPosting.setUniversityId(id);
                    itemPosting.setPrice(100);
                    // itemPosting.setPosterId(posterID);
                    itemPosting.setDescription("test");
                    itemPosting.setDatePosted(new Date(2));
                    itemPosting.setCourseIds(new ArrayList<>());
                    itemPosting.setBuyerId(null);
                    itemPosting.setAvailable(true);

                    url = "http://localhost:8080/itemposting";
                    try {
                        ResponseEntity<ItemPostingResponseDto> itemPostingResponse = helper.post(url,
                                ItemPostingResponseDto.class, itemPosting, true);
                        statusCode = itemPostingResponse.getStatusCode();
                    } catch (HttpClientErrorException e2) {
                        statusCode = e2.getStatusCode();
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @When ("user searches for item postings for university with name {string} and city {string}")
    public void user_searches_for_item_postings_for_university_with_name_and_city(String name, String city) {
        String url = "http://localhost:8080/university/" + city + "/" + name;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<UniversityResponseDto> university = helper.get(url, UniversityResponseDto.class, true);
            if (university.getStatusCode().is2xxSuccessful()) {
                Long id = university.getBody().getId();

                url = "http://localhost:8080/itemposting/university/" + id;

                try {
                    ResponseEntity<ItemPostingResponseDto[]> itemPostings = helper.get(url, ItemPostingResponseDto[].class, true);
                    statusCode = itemPostings.getStatusCode();
                } catch (HttpClientErrorException e) {
                    statusCode = e.getStatusCode();
                }
            }
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
         }
    }

    @Then("a list of item postings for university with name {string} and city {string} is returned")
    public void a_list_of_item_postings_for_university_with_name_and_city_is_returned(String name, String city) {
        assertEquals(HttpStatus.OK, statusCode);
    }

    @Then("no item postings are returned")
    public void no_item_postings_are_returned() {
        assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

}