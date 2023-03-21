package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;

public class RoleResponseDto {

    private Long id;
    private PersonResponseDto person;
    private ModerationRole modRole;

    public RoleResponseDto() {

    }

    public RoleResponseDto(Long id, PersonResponseDto person){
        this.id = id;

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

    public static RoleResponseDto createDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto(
            role.getId(),
            //role.getRole()
            PersonResponseDto.createDto(role.getPerson()));

        return dto;
    }


}