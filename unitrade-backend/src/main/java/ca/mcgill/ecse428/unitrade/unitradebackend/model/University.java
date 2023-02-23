package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class University {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String codename;
    private String description;
    @OneToMany private List<ModerationTier> moderation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModerationTier> getModeration() {
        return moderation;
    }

    public void setModeration(List<ModerationTier> moderation) {
        this.moderation = moderation;
    }

}
