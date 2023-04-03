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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.RoleRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.RoleResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.PersonService;
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

    @Autowired
    PersonService personService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = { "/role/helper" })
    public ResponseEntity<RoleResponseDto> createHelperRole(@RequestBody RoleRequestDto roleRequestDto) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        Person person = personService.getPersonByUsername(roleRequestDto.getPersonUsername());

        Role role = roleService.createRole(authId, person.getId(),
                roleRequestDto.getUniversityId(), ModerationRole.HELPER);
        return new ResponseEntity<RoleResponseDto>(RoleResponseDto.createDto(role), HttpStatus.OK);
    }

    // @ResponseStatus(HttpStatus.OK)
    // @GetMapping(value = { "/role/id/{id}" })
    // public ResponseEntity<RoleResponseDto> getRole(@PathVariable("id") Long id) {
    //     Role role = roleService.getRole(id);
    //     return new ResponseEntity<RoleResponseDto>(
    //             RoleResponseDto.createDto(role), HttpStatus.OK);
    // }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role/person/{personId}" })
    public ResponseEntity<RoleResponseDto> getRoleFromPerson(@PathVariable("personId") Long personId) {
        Role role = roleService.getRoleFromPerson(personId);
        return new ResponseEntity<RoleResponseDto>(
                RoleResponseDto.createDto(role), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role/self/role/university" })
    public ResponseEntity<Long> getSelfRoleUniversityId() {
        Long authId = ControllerHelper.getAuthenticatedUserId();

        return new ResponseEntity<Long>(
            roleService.getSelfRoleUniversityId(authId), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role/personId/{personId}/universityId/{universityId}" })
    public ResponseEntity<RoleResponseDto> getRoleFromPersonAndUniversity(@PathVariable("personId") Long personId,
            @PathVariable("universityId") Long universityId) {
        Role role = roleService.getRoleFromPersonAndUniversity(personId, universityId);
        return new ResponseEntity<RoleResponseDto>(
                RoleResponseDto.createDto(role), HttpStatus.OK);
    }

    // @ResponseStatus(HttpStatus.OK)
    // @PutMapping(value = {"/role/{person}"})
    // public ResponseEntity<RoleResponseDto> updateRole(@RequestBody RoleRequestDto
    // body) {
    // Role role = roleService.updateRole(body.getPersonId(),
    // body.getModRole());

    // return new ResponseEntity<RoleResponseDto>(
    // RoleResponseDto.createDto(role), HttpStatus.OK);
    // }

    // @ResponseStatus(HttpStatus.OK)
    // @GetMapping(value = { "/role/{id}" })
    // public ResponseEntity<List<PersonResponseDto>> getAllPeronsWithRole(@RequestBody RoleRequestDto body) {
    //     List<Person> persons = roleService.getAllPersonsWithRole(body.getId());
    //     List<PersonResponseDto> PersonResponseDtos = new ArrayList<PersonResponseDto>();
    //     for (Person person : persons) {
    //         PersonResponseDtos.add(PersonResponseDto.createDto(person));
    //     }

    //     return new ResponseEntity<List<PersonResponseDto>>(
    //             PersonResponseDtos, HttpStatus.OK);
    // }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/role" })
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleResponseDto> RoleResponseDtos = new ArrayList<RoleResponseDto>();
        for (Role role : roles) {
            RoleResponseDtos.add(RoleResponseDto.createDto(role));
        }

        return new ResponseEntity<List<RoleResponseDto>>(
                RoleResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = { "/role/person/{username}/university/{id}" })
    public ResponseEntity<Void> deleteRole(@PathVariable("username") String username,
            @PathVariable("id") Long universityId) {

        Long authId = ControllerHelper.getAuthenticatedUserId();

        roleService.deleteRole(authId, username, universityId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
