package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Base64;
import java.util.ArrayList;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpStatusCode;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.CourseRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;


public class CreateItemPostingStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;
    //When user creates an item posting with title "CSC108 textbook" and description "textbook for CSC108" and price "50" for university with name "University of Amogus" and city "Amogus"

    @And("a course with codename {string} for university with name {string} and city {string} already exists in the system")
    public void a_course_with_codename_already_exists_in_the_system(String codename, String name, String city) {

        RequestHelperClass helper = new RequestHelperClass(true);
        try {
            helper.get("http://localhost:8080/course/codename/"+codename, CourseRequestDto.class, true);
        } catch (HttpClientErrorException e) {
            CourseRequestDto body = new CourseRequestDto();
            body.setTitle("Test_course");
            body.setCodename(codename);
            body.setDescription("Test_description");

            try {
                ResponseEntity<UniversityResponseDto> response = helper.get("http://localhost:8080/university/"+city+"/"+name, UniversityResponseDto.class, true);
                body.setUniversityId(response.getBody().getId());
                String url = "http://localhost:8080/course";
                try {
                    helper.post(url, CourseRequestDto.class, body, true);
                } catch (HttpClientErrorException e2) {
                    statusCode = e2.getStatusCode();
                }
            } catch (HttpClientErrorException e1) {
                statusCode = e1.getStatusCode();
            }
        }
    }

    @And("a course with codename {string} does not already exist in the system")
    public void a_course_with_codename_does_not_already_exists_in_the_system(String codename) {
        String url = "http://localhost:8080/course/codename/" + codename;

        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            ResponseEntity<CourseResponseDto> response = helper.get(url, CourseResponseDto.class, true);
            CourseResponseDto course = response.getBody();
            assertNotNull(course);
            if (response.getStatusCode().is2xxSuccessful()) {
                url = "http://localhost:8080/course/" + course.getId();
                helper.delete(url, false);
            }
        } catch (HttpClientErrorException e) {

        }
    }

    @When("user attempts to create a item posting with title {string}, description {string}, imagelink {string}, university name {string}, university city {string}, course codename {string}, and price {long}")
    public void user_attempts_to_create_an_item_posting_with_title_description_imamgelink_university_name_university_city_course_codename_and_price(String title, String description, String imageLink, String universityName, String universityCity, String courseCodename, Long price) {
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
        itemPostingRequestDto.setImageLink(imageLink);
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

    @Then("a new itemposting with title {string}, description {string}, imagelink {string}, university name {string}, university city {string}, course codename {string}, and price {long} is added to the system")
    public void the_item_posting_is_created_and_linked_to_the_university_with_name_and_city(String title, String description, String imageLink, String universityName, String universityCity, String courseCodename, Long price) {
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