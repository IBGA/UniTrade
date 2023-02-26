package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

public class CourseRequestDto {
    
    private Long id;
    private String title;
    private String codename;
    private String description;
    private boolean isApproved;
    private Long universityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codeName) {
        this.codename = codeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }
}
