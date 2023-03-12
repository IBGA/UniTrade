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

public class BrowseByUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

    @And ("there are item postings for university with name {string} and city {string} in the system")
    public void there_are_item_postings_for_university_with_name_and_city_in_the_system(String name, String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/university/" + city + "/" + name;

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.getForEntity(url, UniversityResponseDto.class);
            if (university.getStatusCode().is2xxSuccessful()) {
                Long id = university.getBody().getId();
                
                url = "http://localhost:8080/itemposting/university/" + id;

                try {
                    ResponseEntity<ItemPostingResponseDto[]> itemPostings = restTemplate.getForEntity(url, ItemPostingResponseDto[].class);
                    statusCode = itemPostings.getStatusCode();
                } catch (HttpClientErrorException e) {

                    Long posterID = 1L;

                    try {
                        url = "http://localhost:8080/person/email/browsebyuniversity";
                        ResponseEntity<PersonResponseDto> person = restTemplate.getForEntity(url, PersonResponseDto.class);
                        posterID = person.getBody().getId();
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
                        ResponseEntity<PersonResponseDto> personResponse = restTemplate.postForEntity(url, person, PersonResponseDto.class);
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
                        ResponseEntity<ItemPostingResponseDto> itemPostingResponse = restTemplate.postForEntity(url, itemPosting, ItemPostingResponseDto.class);
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
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/university/" + city + "/" + name;

        try {
            ResponseEntity<UniversityResponseDto> university = restTemplate.getForEntity(url, UniversityResponseDto.class);
            if (university.getStatusCode().is2xxSuccessful()) {
                Long id = university.getBody().getId();
                
                url = "http://localhost:8080/itemposting/university/" + id;

                try {
                    ResponseEntity<ItemPostingResponseDto[]> itemPostings = restTemplate.getForEntity(url, ItemPostingResponseDto[].class);
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