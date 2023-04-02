package ca.mcgill.ecse428.unitrade.unitradebackend.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public enum ModerationRole {
        ADMINISTRATOR, HELPER
    }
    @Enumerated(EnumType.STRING)
    private ModerationRole modRole;
    @OneToOne(cascade=CascadeType.DETACH)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;
    @ManyToOne(cascade=CascadeType.DETACH)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private University university;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModerationRole getRole() {
        return modRole;
    }

    public void setRole(ModerationRole modRole) {
        this.modRole = modRole;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
