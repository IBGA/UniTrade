package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String imageLink;
    private Date datePosted;
    private UniversityResponseDto university;
    private PersonResponseDto poster;
    private List<CourseResponseDto> courses;

    public PostResponseDto() {
    }

    public PostResponseDto(Long id, String title, String description, String imageLink, Date datePosted, UniversityResponseDto university, PersonResponseDto poster, List<CourseResponseDto> courses) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.datePosted = datePosted;
        this.university = university;
        this.poster = poster;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public UniversityResponseDto getUniversity() {
        return university;
    }

    public PersonResponseDto getPoster() {
        return poster;
    }

    public List<CourseResponseDto> getCourses() {
        return courses;
    }

    public static PostResponseDto createDto(Post post) {

        List<CourseResponseDto> courses = new ArrayList<CourseResponseDto>();
        for (Course course : post.getCourses()) {
            courses.add(CourseResponseDto.createDto(course));
        }

        UniversityResponseDto university = null;
        PersonResponseDto poster = null;

        if (post.getUniversity() != null) {
            university = UniversityResponseDto.createDto(post.getUniversity());
        }

        if (post.getPoster() != null) {
            poster = PersonResponseDto.createDto(post.getPoster());
        }

        PostResponseDto dto = new PostResponseDto(
            post.getId(),
            post.getTitle(),
            post.getDescription(),
            post.getImageLink(),
            post.getDatePosted(),
            university,
            poster,
            courses
        );
        return dto;
    }
}
