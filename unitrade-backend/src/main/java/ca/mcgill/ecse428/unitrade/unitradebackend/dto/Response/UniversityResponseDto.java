package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

public class UniversityResponseDto {

    private Long id;
    private String name;
    private String city;
    private String description;
    private String moderation;

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
        UniversityResponseDto dto = new UniversityResponseDto();
        dto.id = university.getId();
        dto.name = university.getName();
        dto.city = university.getCity();
        dto.description = university.getDescription();
        dto.moderation = university.getModeration().toString();
        return dto;
    }
}
