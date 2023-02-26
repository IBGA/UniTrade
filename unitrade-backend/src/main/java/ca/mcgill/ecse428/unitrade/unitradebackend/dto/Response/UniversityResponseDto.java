package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import java.util.List;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

public class UniversityResponseDto {

    private Long id;
    private String name;
    private String city;
    private String description;
    private String moderation;

    public UniversityResponseDto() {
    }

    public UniversityResponseDto(Long id, String name, String city, String description, String moderation){
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
        this.moderation = moderation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getModeration() {
        return moderation;
    }

    public static UniversityResponseDto createDto(University university) {
        List<Role> moderation = university.getModeration();
        String modStr = "";
        if (moderation != null) {
            modStr = moderation.toString();
        }
        UniversityResponseDto dto = new UniversityResponseDto(
            university.getId(),
            university.getName(),
            university.getCity(),
            university.getDescription(),
            modStr
        );
        return dto;
    }
}
