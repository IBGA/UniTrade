package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.UniversityResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;

import java.util.Date;
import java.util.List;

public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private Date datePosted;
    private UniversityResponseDto university;
    private PersonResponseDto poster;
    private List<CourseResponseDto> courses;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public University getUniversity() {
        return university;
    }

    public Person getPoster() {
        return poster;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public static PostResponseDto createDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.title = post.getTitle();
        dto.description = post.getDescription();
        dto.datePosted = post.getDatePosted();
        dto.university = UniversityResponseDto.createDto(post.getUniversity());
        dto.poster = PersonResponseDto.createDto(post.getPoster());
        dto.courses = CourseResponseDto.createDto(post.getCourses());

    }
}
