package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
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
    private University university;

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

    public University getUniversity() {
        return university;
    }

    public static PersonResponseDto createPersonDto(Person person) {
        PersonResponseDto personDto = new PersonResponseDto();
        personDto.email = person.getEmail();
        personDto.username = person.getUsername();
        personDto.firstName = person.getFirstName();
        personDto.lastName = person.getLastName();
        personDto.password = person.getPassword();
        personDto.lastOnline = person.getLastOnline();
        personDto.profilePicture = person.getProfilePicture();
        personDto.isOnline = person.isOnline();
        personDto.isEnabled = person.isEnabled();
        personDto.enrolledCourses = person.getEnrolledCourses();
        personDto.university = person.getUniversity();
        return personDto;
    }
}
