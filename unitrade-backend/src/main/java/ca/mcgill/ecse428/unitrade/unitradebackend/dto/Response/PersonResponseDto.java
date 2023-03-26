package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

public class PersonResponseDto {

    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Date lastOnline;
    private String profilePicture;
    private boolean isOnline;
    private boolean isEnabled;
    private List<CourseResponseDto> enrolledCourses;
    private UniversityResponseDto university;

    public PersonResponseDto() {
    }

    public PersonResponseDto(Long id, String email, String username, String firstName, String lastName, String password, Date lastOnline, String profilePicture, boolean isOnline, boolean isEnabled, List<CourseResponseDto> enrolledCourses, UniversityResponseDto university){
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.lastOnline = lastOnline;
        this.profilePicture = profilePicture;
        this.isOnline = isOnline;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public List<CourseResponseDto> getEnrolledCourses() {
        return enrolledCourses;
    }

    public UniversityResponseDto getUniversity() {
        return university;
    }

    public static PersonResponseDto createDto(Person person) {
        return createDto(person, true);
    }


    public static PersonResponseDto createDto(Person person, boolean basicInfoOnly) {
        List<CourseResponseDto> courses = new ArrayList<CourseResponseDto>();
        UniversityResponseDto universityDto = null;

        if (person.getEnrolledCourses() != null) {
            for (Course course : person.getEnrolledCourses()) {
                courses.add(CourseResponseDto.createDto(course));
            }
        }

        if (person.getUniversity() != null) {
            universityDto = UniversityResponseDto.createDto(person.getUniversity());
        }

        Long id = person.getId();
        String email = person.getEmail();
        String password = person.getPassword();

        if (basicInfoOnly) {
            id = null;
            email = null;
            password = null;
        }

        PersonResponseDto dto = new PersonResponseDto(
            id,
            email,
            person.getUsername(),
            person.getFirstName(),
            person.getLastName(),
            password,
            person.getLastOnline(),
            person.getProfilePicture(),
            person.isOnline(),
            person.isEnabled(),
            courses,
            universityDto
        );
        return dto;
    }
}
