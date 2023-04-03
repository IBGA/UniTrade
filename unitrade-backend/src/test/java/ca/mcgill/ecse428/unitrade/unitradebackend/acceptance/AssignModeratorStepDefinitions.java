package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.RoleRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.RoleResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class AssignModeratorStepDefinitions {
    
    @And("user is a moderator for university with name {string} and city {string}")
    public void user_is_a_moderator_for_university_with_name_and_city(String name, String city){
        
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("testUsername2");
        personRequestDto.setEmail("testEmail2");
        personRequestDto.setPassword("testPassword2");
        personRequestDto.setFirstName("testFirstName2");
        personRequestDto.setLastName("testLastName2");

        RequestHelperClass helper = new RequestHelperClass(personRequestDto, true);

        UniversityRequestDto universityDto = new UniversityRequestDto();
        universityDto.setName(name);
        universityDto.setCity(city);
        universityDto.setDescription(city + " is a great city");

        String url = "http://localhost:8080/university";
        ResponseEntity<UniversityResponseDto> response = helper.post(url, UniversityResponseDto.class, universityDto,true);
        UniversityResponseDto university = response.getBody();
        assertNotNull(university);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        personRequestDto.setUniversityId(university.getId());
        ResponseEntity<PersonResponseDto> res = helper.put("http://localhost:8080/person", PersonResponseDto.class, personRequestDto, true);
        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @And("another user with username {string} exists in the system and is a member of university with name {string} and city {string}")
    public void another_user_with_username_exists_in_the_system(String username, String name, String city){
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername(username);
        personRequestDto.setEmail("testEmail3");
        personRequestDto.setPassword("testPassword3");
        personRequestDto.setFirstName("testFirstName3");
        personRequestDto.setLastName("testLastName3");

        RequestHelperClass helper = new RequestHelperClass(personRequestDto, true);

        String url = "http://localhost:8080/university/" + name + "/" + city;

        ResponseEntity<UniversityResponseDto> res1 = helper.get(url, UniversityResponseDto.class, true);
        assertTrue(res1.getStatusCode().is2xxSuccessful());
        personRequestDto.setUniversityId(res1.getBody().getId());

        ResponseEntity<PersonResponseDto> res2 = helper.put("http://localhost:8080/person", PersonResponseDto.class, personRequestDto, true);
        assertTrue(res2.getStatusCode().is2xxSuccessful());
        new RequestHelperClass(personRequestDto, true);
    }

    @When("user attempts to give user with username {string} moderator abilities for university with name {string} and city {string}")
    public void user_attempts_to_give_user_with_username_moderator_abilities_for_university_with_name_and_city(String username, String name, String city){

        RequestHelperClass helper = new RequestHelperClass(false);
        PersonRequestDto personRequestDto = helper.get("http://localhost:8080/person/self", PersonRequestDto.class, true).getBody();

        RoleRequestDto roleDto = new RoleRequestDto();
        roleDto.setPersonUsername(username);
        roleDto.setUniversityId(personRequestDto.getUniversityId());

        String url = "http://localhost:8080/role/helper";

        ResponseEntity<RoleResponseDto> res = helper.post(url, RoleResponseDto.class, roleDto, true);
        assertTrue(res.getStatusCode().is2xxSuccessful());
    }
}
