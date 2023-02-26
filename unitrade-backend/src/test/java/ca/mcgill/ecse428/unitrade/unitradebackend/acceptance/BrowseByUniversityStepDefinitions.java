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

import ca.mcgill.ecse428.unitrade.unitradebackend.controller.ItemPostingRestController;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;

public class BrowseByUniversityStepDefinitions extends AcceptanceTest {

    private HttpStatusCode statusCode;

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

/*
 * Feature: Browse item postings by university

As an existing UniTrade User
I want to be able to browse item postings by university 
So that I can buy items that are relevant to me

Scenario Outline: University and relevant postings are is found (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
When user searches for item postings for university with name "<university_name>" and city "<university_city>"
Then a list of item postings for university with name "<university_name>" and city "<university_city>" is returned

Examples:
| university_name        | university_city |
| University of Toronto  | Toronto         |
| University of Waterloo | Waterloo        |

Scenario Outline: University is not found (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exist in the system
When user searches for item postings for university with name "<university_name>" and city "<university_city>"
Then no item postings are returned

Examples:
| university_name         | university_city |
| University of Amogus    | Amogus          |
| University of Minecraft | Minecraft       |
 */