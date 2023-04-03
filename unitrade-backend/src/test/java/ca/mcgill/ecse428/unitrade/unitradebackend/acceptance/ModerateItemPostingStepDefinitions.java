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

public class ModerateItemPostingStepDefinitions extends AcceptanceTest {

    @And("user is a moderator for university with name {string} and city {string}")
    public void user_is_a_moderator_for_university_with_name_and_city(String university_name, String university_city) {
        
    }

}