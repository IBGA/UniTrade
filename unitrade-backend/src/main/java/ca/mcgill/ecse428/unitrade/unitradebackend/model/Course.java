package ca.mcgill.ecse428.unitrade.unitradebackend.model;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String codename;
    private String description;
    private boolean isApproved;
    @ManyToOne(cascade=CascadeType.DETACH)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private University university;

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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}