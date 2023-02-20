package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

public class PersonResponseDto {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Date lastOnline;
    private String profilePicture;
    private boolean isOnline;
    private boolean isEnabled;
    private List<Course> enrolledCourses;
    private UniversityResponseDto university;

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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public UniversityResponseDto getUniversity() {
        return university;
    }

    public static PersonResponseDto createDto(Person person) {
        PersonResponseDto dto = new PersonResponseDto();
        dto.email = person.getEmail();
        dto.username = person.getUsername();
        dto.firstName = person.getFirstName();
        dto.lastName = person.getLastName();
        dto.password = person.getPassword();
        dto.lastOnline = person.getLastOnline();
        dto.profilePicture = person.getProfilePicture();
        dto.isOnline = person.isOnline();
        dto.isEnabled = person.isEnabled();
        dto.enrolledCourses = person.getEnrolledCourses();
        dto.university = UniversityResponseDto.createDto(person.getUniversity());
        return dto;
    }
}
