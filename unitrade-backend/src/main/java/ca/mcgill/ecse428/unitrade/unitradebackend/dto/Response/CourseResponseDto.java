package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;

public class CourseResponseDto {

    private Long id;
    private String title;
    private String codename;
    private String description;
    private boolean isApproved;
    private UniversityResponseDto university;

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

    public UniversityResponseDto getUniversity() {
        return university;
    }

    public void setUniversity(UniversityResponseDto university) {
        this.university = university;
    }

    public static CourseResponseDto createDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.title = course.getTitle();
        dto.codename = course.getCodename();
        dto.description = course.getDescription();
        dto.isApproved = course.isApproved();
        dto.university = UniversityResponseDto.createDto(course.getUniversity());
        return dto;
    }
}
