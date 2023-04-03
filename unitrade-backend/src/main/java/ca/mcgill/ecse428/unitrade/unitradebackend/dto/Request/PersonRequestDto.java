package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

import java.sql.Date;
import java.util.List;

public class PersonRequestDto {

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
    private List<Long> enrolledCoursesIds;
    private Long universityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Long> getEnrolledCourseIds() {
        return enrolledCoursesIds;
    }

    public void setEnrolledCourseIds(List<Long> enrolledCoursesIds) {
        this.enrolledCoursesIds = enrolledCoursesIds;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }
}