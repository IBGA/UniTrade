package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;

public class RoleResponseDto {

    private Long id;
    private PersonResponseDto person;
    private ModerationRole modRole;
    private UniversityResponseDto university;

    public RoleResponseDto() {

    }

    public RoleResponseDto(Long id, PersonResponseDto person, UniversityResponseDto university){
        this.id = id;
        this.person = person;
        this.university = university;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public ModerationRole getModRole(){
        return modRole;
    }

    public void setModRole(ModerationRole modRole){
        this.modRole = modRole;
    }

    public PersonResponseDto getPerson(){
        return person;
    }

    public void setPerson(PersonResponseDto person){
        this.person = person;
    }

    public UniversityResponseDto getUniversity(){
        return university;
    }

    public void setUniversity(UniversityResponseDto university){
        this.university = university;
    }

    public static RoleResponseDto createDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto(
            role.getId(),
            PersonResponseDto.createDto(role.getPerson()),
            UniversityResponseDto.createDto(role.getUniversity()));

        return dto;
    }
}