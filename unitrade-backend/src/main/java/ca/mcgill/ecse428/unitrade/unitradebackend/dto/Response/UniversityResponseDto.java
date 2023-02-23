package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

public class UniversityResponseDto {

    private Long id;
    private String name;
    private String codename;
    private String description;
    private String moderation;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCodename() {
        return codename;
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
        dto.codename = university.getCodename();
        dto.description = university.getDescription();
        dto.moderation = university.getModeration().toString();
        return dto;
    }
}
