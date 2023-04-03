package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;

public class ItemPostingResponseDto extends PostResponseDto {
    
    private boolean isAvailable;
    private Double price;
    private PersonResponseDto buyer;

    public ItemPostingResponseDto() {
    }

    public ItemPostingResponseDto(Long id, String title, String description, String imageLink, Date datePosted, UniversityResponseDto university, PersonResponseDto poster, List<CourseResponseDto> courses, boolean isAvailable, Double price, PersonResponseDto buyer){
        super(id, title, description, imageLink, datePosted, university, poster, courses);
        this.isAvailable = isAvailable;
        this.price = price;
        this.buyer = buyer;
    }

    public PersonResponseDto getBuyer() {
        return buyer;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public static ItemPostingResponseDto createDto(ItemPosting itemPosting) {
        List<CourseResponseDto> courses = new ArrayList<CourseResponseDto>();
        for (Course course : itemPosting.getCourses()) {
            courses.add(CourseResponseDto.createDto(course));
        }

        PersonResponseDto buyer = null;
        UniversityResponseDto university = null;

        if (itemPosting.getBuyer() != null) {
            buyer = PersonResponseDto.createDto(itemPosting.getBuyer());
        }

        if (itemPosting.getUniversity() != null) {
            university = UniversityResponseDto.createDto(itemPosting.getUniversity());
        }

        ItemPostingResponseDto dto = new ItemPostingResponseDto(
            itemPosting.getId(),
            itemPosting.getTitle(),
            itemPosting.getDescription(),
            itemPosting.getImageLink(),
            itemPosting.getDatePosted(),
            university,
            PersonResponseDto.createDto(itemPosting.getPoster()),
            courses,
            itemPosting.isAvailable(),
            itemPosting.getPrice(),
            buyer
        );
        return dto;
    }
}
