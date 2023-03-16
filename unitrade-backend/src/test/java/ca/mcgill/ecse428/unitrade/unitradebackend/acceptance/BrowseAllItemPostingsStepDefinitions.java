package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;
import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.Base64;
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

public class BrowseAllItemPostingsStepDefinitions extends AcceptanceTest {
        HttpStatusCode statusCode;
        ResponseEntity<ItemPostingResponseDto[]> response;

        @And("there exists at least one item posting")
        public void there_exists_at_least_one_item_posting() {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/itemposting";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
            headers.set("Access-Control-Allow-Credentials", "true");
    
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                restTemplate.exchange(url, HttpMethod.GET, entity, ItemPostingResponseDto[].class);
            } catch (HttpClientErrorException e) {
                //create posting
            }
        }

        @When("user searches for all available item postings")
        public void user_searches_for_all_available_item_postings() {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/itemposting";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
            headers.set("Access-Control-Allow-Credentials", "true");
    
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.GET, entity, ItemPostingResponseDto[].class);
                statusCode = response.getStatusCode();
            } catch (HttpClientErrorException e) {
                statusCode = e.getStatusCode();
            }
        }

        @Then("all available item postings are displayed")
        public void all_available_item_postings_are_displayed() {
            assertEquals(statusCode, HttpStatus.OK);
        }

        @And("there are no available item postings")
        public void there_are_no_available_item_postings() {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/itemposting";


            HttpHeaders headers = new HttpHeaders();

            headers.set("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                response = restTemplate.exchange(url, HttpMethod.GET, entity, ItemPostingResponseDto[].class);
                for (ItemPostingResponseDto itemPosting : response.getBody()) {
                    restTemplate.exchange(url + "/" + itemPosting.getId(), HttpMethod.DELETE, entity, ItemPostingResponseDto[].class);
                }
            } catch (HttpClientErrorException e) {
                //do nothing
            }
        }

        @Then("no item postings are displayed")
        public void no_item_postings_are_displayed() {
            assertEquals(0, response.getBody().length);
        }
    }
    


/*
 * Feature: Browse all available item postings

As an existing UniTrade user
I want to browse all available item postings
So that I can find items I may be interested in

Scenario: Browse all available item postings (Normal Flow)
Given user is logged in
And there exists at least one item posting
When user searches for all available item postings
Then all available item postings are displayed

Scenario: No available item postings (Alternate Flow)
Given user is logged in
And there are no available item postings
When user searches for all available item postings
Then no item postings are displayed
 */