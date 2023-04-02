package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditItemPostingStepDefinitions extends AcceptanceTest {
    HttpStatusCode statusCode;

    @And("item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} exists in the system")
    public void item_posting_with_title_description_price_for_university_in_city_exists_in_the_system(String title, String description, Long price, String university, String city) {
        
        RequestHelperClass helper = new RequestHelperClass(true);

        try {
            
        } catch (HttpClientErrorException e) {

            ItemPostingRequestDto body = new ItemPostingRequestDto();
            body.setTitle(title);
            body.setDescription(description);
            body.setImageLink("https://liftlearning.com/wp-content/uploads/2020/09/default-image.png");
            body.setDatePosted(new Date(System.currentTimeMillis()));
            body.setAvailable(true);
            body.setPrice(price);

            try {
                ResponseEntity<UniversityResponseDto> response = helper.get("http://localhost:8080/university/"+city+"/"+university, UniversityResponseDto.class, true);
                body.setUniversityId(response.getBody().getId());
            } catch (HttpClientErrorException e1) {
                statusCode = e1.getStatusCode();
            }
        }
    }

    @And("user is the owner of the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string}")
    public void user_is_owner_of_item_posting_with_title_description_price_for_university_in_city(String title, String description, Long price, String university, String city) {

    }

    @When("user edits the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} to have description {string} and price {long}")
    public void user_edits_item_posting_with_title_description_price_for_university_in_city_to_description_and_price(String title, String description, Long price, String university, String city, String newDescription, Long newPrice) {

    }

    @Then("the item posting with title {string} and description {string} and price {long} for university with name {string} and city {string} exists in the system")
    public void then_item_posting_with_title_description_price_for_university_in_city_exists_in_the_system(String title, String description, Long price, String university, String city) {

    }
}
