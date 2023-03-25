package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Base64;
import java.util.ArrayList;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;


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


public class CreateItemPostingStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;
    //When user creates an item posting with title "CSC108 textbook" and description "textbook for CSC108" and price "50" for university with name "University of Amogus" and city "Amogus"

    @When("user creates an item posting with title {string} and description {string} and price {long} for university with name {string} and city {string}")
    public void user_creates_an_item_posting_with_title_and_description_and_price_for_university_with_name_and_city(String title, String description, Long price, String universityName, String universityCity) {
        RestTemplate restTemplate = new RestTemplate();
        Long universityId = null;
        //Long price = Long.parseLong(itemPrice);

        String url = "http://localhost:8080/university/" + universityCity + "/" + universityName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UniversityResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    UniversityResponseDto.class);
            universityId = response.getBody().getId();
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
            if(statusCode.value() == 404) return;
        }

        url = "http://localhost:8080/itemposting";

        ItemPostingRequestDto itemPostingRequestDto = new ItemPostingRequestDto();
        itemPostingRequestDto.setTitle(title);
        itemPostingRequestDto.setDescription(description);
        itemPostingRequestDto.setPrice(price);
        itemPostingRequestDto.setUniversityId(universityId);
        // itemPostingRequestDto.setPosterId(posterId);
        itemPostingRequestDto.setDatePosted(new Date(2));
        itemPostingRequestDto.setCourseIds(new ArrayList<>());
        itemPostingRequestDto.setBuyerId(null);
        itemPostingRequestDto.setAvailable(true);

        HttpEntity<ItemPostingRequestDto> itemPostingEntity = new HttpEntity<>(itemPostingRequestDto, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, itemPostingEntity, ItemPostingResponseDto.class);
        } catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
    }

    @Then("the item posting is created and linked to the university with name {string} and city {string}")
    public void the_item_posting_is_created_and_linked_to_the_university_with_name_and_city(String universityName, String universityCity) {
        RestTemplate restTemplate = new RestTemplate();
        Long universityId = null;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
        headers.set("Access-Control-Allow-Credentials", "true");

        String url = "http://localhost:8080/university/" + universityCity + "/" + universityName;

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UniversityResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                UniversityResponseDto.class);

        universityId = response.getBody().getId();

        url = "http://localhost:8080/itemposting/university/" + universityId;
        ResponseEntity<ItemPostingResponseDto[]> itemPostings = restTemplate.exchange(url, HttpMethod.GET, entity,
                ItemPostingResponseDto[].class);

        for (ItemPostingResponseDto itemPosting : itemPostings.getBody()) {
            if (itemPosting.getTitle().equals("CSC108 textbook")) {
                assertEquals("CSC108 textbook", itemPosting.getTitle());
                assertEquals("textbook for CSC108", itemPosting.getDescription());
                assertEquals(50, itemPosting.getPrice());
            } else if (itemPosting.getTitle().equals("MTH101 textbook")) {
                assertEquals("MTH101 textbook", itemPosting.getTitle());
                assertEquals("textbook for MTH101", itemPosting.getDescription());
                assertEquals(50, itemPosting.getPrice());
            }
        }

    }

    @Then("the item posting is not created and an error is thrown")
    public void the_item_posting_is_not_created_and_an_error_is_thrown() {
        assertEquals(404, statusCode.value());
    }
}

/* 

Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario Outline: University is found in the system and item posting is created (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
When user creates an item posting with title "<title>" and description "<description>" and price "<price>" for university with name "<university_name>" and city "<university_city>"
Then the item posting is created and linked to the university with name "<university_name>" and city "<university_city>"

| university_name | university_city | title | description | price |
| University of Toronto | Toronto | CSC108 textbook | textbook for CSC108 | 50 |
| University of Toronto | Toronto | MTH101 textbook | textbook for MTH101 | 50 |

Scenario: University is not found in the system and item posting is not created (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exist in the system
When user creates an item posting with title "<title>" and description "<description>" and price "<price>" for university with name "<university_name>" and city "<university_city>"
Then the item posting is not created and an error is thrown

| university_name | university_city | title | description | price |
| University of Amogus | Amogus | CSC108 textbook | textbook for CSC108 | 50 |
| University of Amogus | Amogus | MTH101 textbook | textbook for MTH101 | 50 |







*/