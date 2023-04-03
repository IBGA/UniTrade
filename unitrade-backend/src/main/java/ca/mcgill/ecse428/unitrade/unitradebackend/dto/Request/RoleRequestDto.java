package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

public class RoleRequestDto {
    private Long id;
    private String personUsername;
    private Long universityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonUsername() {
        return personUsername;
    }

    public void setPersonUsername(String personUsername) {
        this.personUsername = personUsername;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }
}
