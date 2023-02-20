package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
public class PersonRestController {

    @Autowired
    PersonService personService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/person" })
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto body) {
        Person person = personService.createPerson(
                body.getEmail(),
                body.getUsername(),
                body.getFirstName(),
                body.getLastName(),
                body.getPassword(),
                body.getProfilePicture(),
                body.getEnrolledCourses(),
                body.getUniversityId());
        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(person), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/person" })
    public ResponseEntity<PersonResponseDto> getPerson(@RequestBody PersonRequestDto body) {
        return new ResponseEntity<PersonResponseDto>(
                PersonResponseDto.createDto(personService.getPerson(body.getId())),
                HttpStatus.CREATED);
    }
}
