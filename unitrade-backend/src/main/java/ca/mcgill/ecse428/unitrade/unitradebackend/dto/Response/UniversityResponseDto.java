package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

public class UniversityResponseDto {

    private Long id;
    private String name;
    private String city;
    private String description;

    public UniversityResponseDto() {
    }

    public UniversityResponseDto(Long id, String name, String city, String description){
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
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

    public static UniversityResponseDto createDto(University university) {
        UniversityResponseDto dto = new UniversityResponseDto(
            university.getId(),
            university.getName(),
            university.getCity(),
            university.getDescription()
        );
        return dto;
    }
}
