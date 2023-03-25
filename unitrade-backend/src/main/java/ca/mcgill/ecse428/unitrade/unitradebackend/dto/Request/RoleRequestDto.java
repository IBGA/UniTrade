package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;

public class RoleRequestDto {
    private Long id;
    private Long personId;
    private ModerationRole modRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModerationRole getModRole(){
        return modRole;
    }

    public void setModRole(ModerationRole modRole){
        this.modRole = modRole;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
