package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.PersonService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "${allowed.origins}")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
@PreAuthorize("hasRole('USER')")
public class PersonRestController {

    @Autowired
    PersonService personService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/person" })
    @PreAuthorize("permitAll()")
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto body) {
        Person person = personService.createPerson(
                body.getEmail(),
                body.getUsername(),
                body.getFirstName(),
                body.getLastName(),
                body.getPassword(),
                body.getProfilePicture(),
                body.getUniversityId());
        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(person, false), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/person/self" })
    public ResponseEntity<PersonResponseDto> getPerson() {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.getPerson(authId), false), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll()")
    @GetMapping(value = { "/person/username/{username}" })
    public ResponseEntity<PersonResponseDto> getPersonByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.getPersonByUsername(username)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll()")
    @GetMapping(value = { "/person/usernames" })
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = personService.getAllUsernames();
        return new ResponseEntity<List<String>>(usernames, HttpStatus.OK);
    }

//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person/id/{id}" })
//     public ResponseEntity<PersonResponseDto> getPerson(@PathVariable("id") Long id) {
//         return new ResponseEntity<PersonResponseDto>(
//                 PersonResponseDto.createDto(personService.getPerson(id), true), HttpStatus.OK);
//     }

//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person/email/{email}" })
//     public ResponseEntity<PersonResponseDto> getPersonByEmail(@PathVariable("email") String email) {
//         return new ResponseEntity<PersonResponseDto>(
//                 PersonResponseDto.createDto(personService.getPersonByEmail(email), true), HttpStatus.OK);
//     }

//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person" })
//     public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
//         List<Person> persons = personService.getAllPersons();
//         List<PersonResponseDto> personResponseDtos = new ArrayList<PersonResponseDto>();
//         for (Person person : persons) {
//             personResponseDtos.add(PersonResponseDto.createDto(person));
//         }

//         return new ResponseEntity<List<PersonResponseDto>>(
//                 personResponseDtos, HttpStatus.OK);
//     }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/person/exists/{email}" })
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> personExists(@PathVariable("email") String email) {
        return new ResponseEntity<Boolean>(
                personService.personExistsWithEmail(email), HttpStatus.OK);
    }
//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person/id/{id}" })
//     public ResponseEntity<PersonResponseDto> getPerson(@PathVariable("id") Long id) {
//         return new ResponseEntity<PersonResponseDto>(
//                 PersonResponseDto.createDto(personService.getPerson(id), true), HttpStatus.OK);
//     }

//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person/email/{email}" })
//     public ResponseEntity<PersonResponseDto> getPersonByEmail(@PathVariable("email") String email) {
//         return new ResponseEntity<PersonResponseDto>(
//                 PersonResponseDto.createDto(personService.getPersonByEmail(email), true), HttpStatus.OK);
//     }

//     @ResponseStatus(HttpStatus.OK)
//     @GetMapping(value = { "/person" })
//     public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
//         List<Person> persons = personService.getAllPersons();
//         List<PersonResponseDto> personResponseDtos = new ArrayList<PersonResponseDto>();
//         for (Person person : persons) {
//             personResponseDtos.add(PersonResponseDto.createDto(person));
//         }

//         return new ResponseEntity<List<PersonResponseDto>>(
//                 personResponseDtos, HttpStatus.OK);
//     }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/person/exists/email/{email}" })
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> personExistsWithEmail(@PathVariable("email") String email) {
        return new ResponseEntity<Boolean>(
                personService.personExistsWithEmail(email), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/person/exists/username/{username}" })
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> personExistsWithUsername(@PathVariable("username") String username) {
        return new ResponseEntity<Boolean>(
                personService.personExistsWithUsername(username), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/person/non-helper/from/university/{universityId}"})
    public ResponseEntity<List<PersonResponseDto>> getPersonsNonHelperFromUniversity(@PathVariable("universityId") Long universityId) {

        List<Person> persons = personService.getPersonsNonHelperFromUniversity(universityId);
        List<PersonResponseDto> personResponseDtos = new ArrayList<PersonResponseDto>();
        for (Person person : persons) {
            personResponseDtos.add(PersonResponseDto.createDto(person));
        }

        return new ResponseEntity<List<PersonResponseDto>>(
                personResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/person/helper/from/university/{universityId}"})
    public ResponseEntity<List<PersonResponseDto>> getPersonsHelperFromUniversity(@PathVariable("universityId") Long universityId) {

        List<Person> persons = personService.getPersonsHelperFromUniversity(universityId);
        List<PersonResponseDto> personResponseDtos = new ArrayList<PersonResponseDto>();
        for (Person person : persons) {
            personResponseDtos.add(PersonResponseDto.createDto(person));
        }
        return new ResponseEntity<List<PersonResponseDto>>(
                personResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/person/check/admin/or/helper"})
    public ResponseEntity<Boolean> checkIfUserIsAdminOrHelper() {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<Boolean>(
                personService.isAdministratorOrHelperToSelfUniversity(authId), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = { "/person" })
    public ResponseEntity<PersonResponseDto> updatePersonInformation(@RequestBody PersonRequestDto body) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.updatePersonInformation(
                        authId,
                        body.getFirstName(),
                        body.getLastName(),
                        body.getProfilePicture())),
                HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = { "/person/password" })
    public ResponseEntity<PersonResponseDto> updatePersonPassword(@RequestBody PersonRequestDto body) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.updatePersonPassword(
                        authId,
                        body.getPassword())),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = { "/person/enrolledCourses" })
    public ResponseEntity<PersonResponseDto> updatePersonEnrolledCourses(@RequestBody PersonRequestDto body) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.updatePersonEnrolledCourses(
                        authId,
                        body.getEnrolledCourseIds())),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = { "/person/universityId" })
    public ResponseEntity<PersonResponseDto> updatePersonCurrentUniversity(@RequestBody PersonRequestDto body) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.updatePersonCurrentUniversity(
                        authId,
                        body.getUniversityId())),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = { "/person" })
    public ResponseEntity<PersonResponseDto> deletePerson() {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        personService.deletePerson(authId);
        return new ResponseEntity<PersonResponseDto>(HttpStatus.OK);
    }
}
