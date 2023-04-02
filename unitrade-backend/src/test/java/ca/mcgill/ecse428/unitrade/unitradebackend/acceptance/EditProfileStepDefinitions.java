package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.CourseRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.PersonService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class EditProfileStepDefinitions extends AcceptanceTest {
        @Autowired PersonService personService;
        HttpStatusCode statusCode;
        ResponseEntity<ItemPostingResponseDto[]> response;

        @And("user edits profile information with profile picture {string}, university with name {string} and city {string}, and enrolled course with codename {string}")
        public void user_edits_profile_information_with_profile_picture_university_with_name_and_city_and_enrolled_course_with_codename(String profilePicture, String universityName, String universityCity, String codename) {
            var username = "editProfile";
            // Create the person
            PersonRequestDto personDto = new PersonRequestDto();
            personDto.setEmail(username + "@profile.com");
            personDto.setUsername(username);
            personDto.setPassword("doesntmatter");
            personDto.setFirstName("doesntmatter");
            personDto.setLastName("doesntmatter");
            RequestHelperClass helper = new RequestHelperClass(personDto, true);

            var person = personService.getPersonByUsername(personDto.getUsername());

            // Create the university
            UniversityRequestDto universityDto = new UniversityRequestDto();
            universityDto.setName(universityName);
            universityDto.setCity(universityCity);
            universityDto.setDescription("doesntmatter");
            var universityResponse = helper.post("http://localhost:8080/university", UniversityResponseDto.class, universityDto, true);

            // Create the course
            CourseRequestDto courseDto = new CourseRequestDto();
            courseDto.setTitle("editProfileTest");
            courseDto.setDescription("doesnt matter");
            courseDto.setCodename(codename);
            courseDto.setUniversityId(universityResponse.getBody().getId());

            var courseResponse = helper.post("http://localhost:8080/course", CourseRequestDto.class, courseDto, true);

            // Now do the put request to modify the user's profile
            personDto.setId(person.getId());
            personDto.setUsername(username);
            personDto.setProfilePicture(profilePicture);
            personDto.setUniversityId(universityResponse.getBody().getId());
            personDto.setEnrolledCourseIds(List.of(courseResponse.getBody().getId()));

            helper.put("http://localhost:8080/person/universityId", PersonRequestDto.class, personDto, true);
            helper.put("http://localhost:8080/person/enrolledCourses", PersonRequestDto.class, personDto, true);
            helper.put("http://localhost:8080/person", PersonRequestDto.class, personDto, true );
        }

        @Then("user's profile information is updated with profile picture {string}, university with name {string} and city {string}, and enrolled course with codename {string}")
        public void user_s_profile_information_is_updated_with_profile_picture_university_with_name_and_city_and_enrolled_course(String profilePicture, String universityName, String Universitycity, String codename) {
            var username = "editProfile";

            var person = personService.getPersonByUsername(username);

            assertEquals(username, person.getUsername());
            assertEquals(profilePicture, person.getProfilePicture());
            assertEquals(universityName, person.getUniversity().getName());
            assertEquals(Universitycity, person.getUniversity().getCity());
            assertEquals(codename, person.getEnrolledCourses().get(0).getCodename());
        }

}