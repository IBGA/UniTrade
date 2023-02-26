package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;

public class CourseResponseDto {

    private Long id;
    private String title;
    private String codename;
    private String description;
    private boolean isApproved;
    private UniversityResponseDto university;

    public CourseResponseDto(Long id, String title, String codename, String description, boolean isApproved, UniversityResponseDto university){
        this.id = id;
        this.title = title;
        this.codename = codename;
        this.description = description;
        this.isApproved = isApproved;
        this.university = university;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCodename() {
        return codename;
    }

    public String getDescription() {
        return description;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public UniversityResponseDto getUniversity() {
        return university;
    }

    public static CourseResponseDto createDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto(
            course.getId(),
            course.getTitle(),
            course.getCodename(),
            course.getDescription(),
            course.isApproved(),
            UniversityResponseDto.createDto(course.getUniversity())
        );
        return dto;
    }
}
