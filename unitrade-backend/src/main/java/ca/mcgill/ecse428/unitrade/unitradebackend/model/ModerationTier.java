package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

@Entity
public class ModerationTier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public enum ModerationRole {
        ADMINISTRATOR, HELPER
    }
    private ModerationRole role;
    @OneToOne private Person person;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModerationRole getRole() {
        return role;
    }

    public void setRole(ModerationRole role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
