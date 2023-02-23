package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.UniversityRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.UniversityService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "University created"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
    @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
    @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
public class UniversityRestController {

    @Autowired
    UniversityService universityService;

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/university" })
    public ResponseEntity<UniversityResponseDto> createUniversity(@RequestBody UniversityRequestDto body) {
        University university = universityService.createUniversity(
            body.getName(), 
            body.getCity(), 
            body.getDescription());
        return new ResponseEntity<UniversityResponseDto>(
            UniversityResponseDto.createDto(university), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/university/{id}" })
    public ResponseEntity<UniversityResponseDto> getUniversity(@PathVariable("id") Long id) {
        University university = universityService.getUniversity(id);
        return new ResponseEntity<UniversityResponseDto>(
            UniversityResponseDto.createDto(university), HttpStatus.OK);
    }

}
