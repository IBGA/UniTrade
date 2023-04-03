package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.CourseRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditItemPostingStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;
    RequestHelperClass helper = new RequestHelperClass(true);
    ResponseEntity<UniversityResponseDto> universityResponse;
    ResponseEntity<ItemPostingResponseDto> itemPostingResponse;
    Long itemPostingId;

    @And("item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} exists in the system")
    public void item_posting_with_title_description_price_for_university_in_city_exists_in_the_system(String title, String description, Long price, String name, String city) {

        ItemPostingRequestDto body = new ItemPostingRequestDto();
        body.setTitle(title);
        body.setDescription(description);
        body.setImageLink("https://liftlearning.com/wp-content/uploads/2020/09/default-image.png");
        body.setDatePosted(new Date(System.currentTimeMillis()));
        body.setAvailable(true);
        body.setPrice(price);
        Long uniID;

        try {
            universityResponse = helper.get("http://localhost:8080/university/"+city+"/"+name, UniversityResponseDto.class, true);
            uniID = universityResponse.getBody().getId();
            body.setUniversityId(uniID);
        } catch (HttpClientErrorException e3) {
            //create university
            UniversityRequestDto universityDto = new UniversityRequestDto();
            universityDto.setName(name);
            universityDto.setCity(city);
            universityDto.setDescription("doesntmatter");
            var universityPost = helper.post("http://localhost:8080/university", UniversityResponseDto.class, universityDto, true);
            uniID = universityPost.getBody().getId();
            body.setUniversityId(uniID);
        }

        try {
            helper.get("http://localhost:8080/course/codename/ECSE2334", CourseRequestDto.class, true);
        } catch (HttpClientErrorException e) {
            CourseRequestDto courseBody = new CourseRequestDto();
            courseBody.setTitle("Test_ceourse");
            courseBody.setCodename("ECSE2334");
            courseBody.setDescription("Test_descrieption");
            courseBody.setUniversityId(uniID);
            
            var coursePost = helper.post("http://localhost:8080/course", CourseResponseDto.class, courseBody, true);
            
        }

        ResponseEntity<CourseResponseDto> courseResponse = helper.get("http://localhost:8080/course/codename/ECSE2334", CourseResponseDto.class, true);
        List<Long> courseIds = List.of(courseResponse.getBody().getId());
        body.setCourseIds(courseIds);

        var itemPostingPost = helper.post("http://localhost:8080/itemposting", ItemPostingResponseDto.class, body, true);
        itemPostingId = itemPostingPost.getBody().getId();
    
    }

    @And("user is the owner of the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string}")
    public void user_is_owner_of_item_posting_with_title_description_price_for_university_in_city(String title, String description, Long price, String name, String city) {
        

    }

    @When("user edits the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} to have description {string} and price {long}")
    public void user_edits_item_posting_with_title_description_price_for_university_in_city_to_description_and_price(String title, String description, Long price, String name, String city, String newDescription, Long newPrice) {
        itemPostingResponse = helper.get("http://localhost:8080/itemposting/"+itemPostingId, ItemPostingResponseDto.class, true);
        ItemPostingRequestDto body = new ItemPostingRequestDto();
        body.setDescription(newDescription);
        body.setPrice(newPrice);
        body.setAvailable(true);
        body.setTitle(title);
        body.setImageLink("https://liftlearning.com/wp-content/uploads/2020/09/default-image.png");
        var itemPostingPut = helper.put("http://localhost:8080/itemposting/"+itemPostingId, ItemPostingResponseDto.class, body, true);
    }

    @Then("the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} exists in the system")
    public void then_item_posting_with_title_description_price_for_university_in_city_exists_in_the_system(String title, String newDescription, Long newPrice, String name, String city) {
        itemPostingResponse = helper.get("http://localhost:8080/itemposting/"+itemPostingId, ItemPostingResponseDto.class, true);
        assert(itemPostingResponse.getBody().getDescription().equals(newDescription));
        assert(itemPostingResponse.getBody().getPrice().equals(newPrice));
    }

    @When("user deletes the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string}")
    public void user_deletes_item_posting_with_title_description_price_for_university_in_city(String title, String description, Long price, String name, String city) {
        var itemPostingDelete = helper.delete("http://localhost:8080/itemposting/"+itemPostingId, true);
    }

    @Then("the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} does not exist in the system") 
    public void then_item_posting_with_title_description_price_for_university_in_city_does_not_exist_in_the_system(String title, String description, Long price, String name, String city) {
        try {
            itemPostingResponse = helper.get("http://localhost:8080/itemposting/"+itemPostingId, ItemPostingResponseDto.class, true);
        } catch (HttpClientErrorException e) {
            assert(e.getStatusCode().equals(HttpStatus.NOT_FOUND));
        }
    }
}

/*
 * Feature: Edit item posting

As a student
I want to edit or delete my item postings
So that I can keep them up to date

Scenario Outline: Item posting is edited (Normal Flow)
Given user is logged in
And item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" exists in the system
And user is the owner of the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
When user edits the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" to have description "<new_description>" and price <new_price>
Then the item posting with title "<title>" and description "<new_description>" and price <new_price> for university with name "<university_name>" and city "<university_city>" exists in the system

Examples:
| university_name        | university_city | title           | description         | price | new_description          | new_price |
| University of Montreal | Montreal        | CSC109 textbook | textbook for CSC109 | 50    | mint textbook for CSC109 | 60        |
| University of Montreal | Montreal        | MTH103 textbook | textbook for MTH103 | 50    | mint textbook for MTH103 | 60        |

Scenario: Item posting is deleted (Alternate Flow)
Given user is logged in
And item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" exists in the system
And user is the owner of the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
When user deletes the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
Then the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" does not exist in the system

Examples:
| university_name        | university_city | title           | description         | price |
| University of Montreal | Montreal        | CSC109 textbook | textbook for CSC109 | 50    |
| University of Montreal | Montreal        | MTH103 textbook | textbook for MTH103 | 50    |
 */