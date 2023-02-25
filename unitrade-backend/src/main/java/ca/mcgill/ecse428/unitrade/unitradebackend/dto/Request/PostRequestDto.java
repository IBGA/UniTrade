package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

import java.sql.Date;
import java.util.List;

public class PostRequestDto {
    private Long id;
    private String title;
    private String description;
    private Date datePosted;
    private Long universityId;
    private Long posterId;
    private List<Long> courseIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Long getUniversity() {
        return universityId;
    }

    public void setUniversity(Long universityId) {
        this.universityId = universityId;
    }

    public Long getPoster() {
        return posterId;
    }

    public void setPoster(Long posterId) {
        this.posterId = posterId;
    }

    public List<Long> getCourses() {
        return courseIds;
    }

    public void setCourses(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
