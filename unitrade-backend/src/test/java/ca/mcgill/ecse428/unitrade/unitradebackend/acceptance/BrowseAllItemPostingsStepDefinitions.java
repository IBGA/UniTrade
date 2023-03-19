package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.http.HttpStatusCode;


import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;



public class BrowseAllItemPostingsStepDefinitions extends AcceptanceTest {
        HttpStatusCode statusCode;
        ResponseEntity<ItemPostingResponseDto[]> response;

        @And("there exists at least one item posting")
        public void there_exists_at_least_one_item_posting() {
            String url = "http://localhost:8080/itemposting";

            RequestHelperClass helper = new RequestHelperClass(true);

            try {
                helper.get(url, ItemPostingResponseDto[].class, true);
            } catch (HttpClientErrorException e) {
                //create posting
            }
        }

        @When("user searches for all available item postings")
        public void user_searches_for_all_available_item_postings() {
            String url = "http://localhost:8080/itemposting";

            RequestHelperClass helper = new RequestHelperClass(true);

            try {
                response = helper.get(url, ItemPostingResponseDto[].class, true);
                statusCode = response.getStatusCode();
            } catch (HttpClientErrorException e) {
                statusCode = e.getStatusCode();
            }
        }

        @Then("all available item postings are displayed")
        public void all_available_item_postings_are_displayed() {
            assertEquals(200, statusCode.value());
        }

        @And("there are no available item postings")
        public void there_are_no_available_item_postings() {
            String url = "http://localhost:8080/itemposting";

            RequestHelperClass helper = new RequestHelperClass(true);

            try {
                response = helper.get(url, ItemPostingResponseDto[].class, true);
                ItemPostingResponseDto[] itemPostings = response.getBody();
                assertNotNull(itemPostings);
                for (ItemPostingResponseDto itemPosting : itemPostings) {
                    helper.delete(url + "/" + itemPosting.getId(), true);
                }
            } catch (HttpClientErrorException e) {
                //do nothing
            }
        }

        @Then("no item postings are displayed")
        public void no_item_postings_are_displayed() {
            ItemPostingResponseDto[] itemPostings = response.getBody();
            assertNotNull(itemPostings);
            assertEquals(0, itemPostings.length);
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