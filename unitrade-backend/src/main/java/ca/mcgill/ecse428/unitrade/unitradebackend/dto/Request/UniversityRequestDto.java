package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

import java.util.List;

public class UniversityRequestDto {

    private Long id;

    private String name;
    private String city;
    private String description;
    private List<Long> moderationIds;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getModerationIds() {
        return moderationIds;
    }

    public void setModerationIds(List<Long> moderationIds) {
        this.moderationIds = moderationIds;
    }

}
