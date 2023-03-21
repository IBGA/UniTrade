package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.RoleRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.RoleResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.RoleService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "${allowed.origins}")
@RestController
@PreAuthorize("hasRole('USER')")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})

public class RoleRestController {

    @Autowired
    RoleService roleService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role/{id}" })
    public ResponseEntity<RoleResponseDto> getRole(@PathVariable("id") Long id) {
        Role role = roleService.getRole(id);
        return new ResponseEntity<RoleResponseDto>(
                RoleResponseDto.createDto(role), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role/{person}" })
    public ResponseEntity<RoleResponseDto> getRole(@PathVariable("person") Person person) {
        Role role = roleService.getRole(person);
        return new ResponseEntity<RoleResponseDto>(
                RoleResponseDto.createDto(role), HttpStatus.OK);
    } 

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = {"/role/{person}"})
    public ResponseEntity<RoleResponseDto> updateRole(@RequestBody RoleRequestDto body) {
      Role role = roleService.updateRole(body.getPersonId(),
                                        body.getModRole());

      return new ResponseEntity<RoleResponseDto>(
            RoleResponseDto.createDto(role), HttpStatus.OK);
                                          
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = {"/role/{id}"})
    public ResponseEntity<List<PersonResponseDto>> getAllPeronsWithRole(@RequestBody RoleRequestDto body){
      List<Person> persons = roleService.getAllPersonsWithRole(body.getId());
        List<PersonResponseDto> PersonResponseDtos = new ArrayList<PersonResponseDto>();
        for (Person person : persons) {
            PersonResponseDtos.add(PersonResponseDto.createDto(person));
        }

        return new ResponseEntity<List<PersonResponseDto>>(
                PersonResponseDtos, HttpStatus.OK);
    }
  }

