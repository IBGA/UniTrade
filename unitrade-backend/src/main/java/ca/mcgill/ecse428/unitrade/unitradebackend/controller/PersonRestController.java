package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.PersonService;

public class PersonRestController {

    @Autowired PersonService personService;

    @PostMapping(value = {"/person"})
    public ResponseEntity<PersonResponseDto> createArtwork(@RequestBody PersonRequestDto body) {
        Person artwork =
                personService.CreatePerson(
                        body.getEmail(),
                        body.getUsername(),
                        body.getFirstName()
                    );
        return new ResponseEntity<PersonResponseDto>(
            PersonResponseDto.createPersonDto(artwork), HttpStatus.CREATED);
    }
}
