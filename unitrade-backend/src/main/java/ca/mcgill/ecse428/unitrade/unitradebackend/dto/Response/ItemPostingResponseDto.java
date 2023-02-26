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

    public ItemPostingResponseDto(Long id, String title, String description, Date datePosted, UniversityResponseDto university, PersonResponseDto poster, List<CourseResponseDto> courses, boolean isAvailable, Double price, PersonResponseDto buyer){
        super(id, title, description, datePosted, university, poster, courses);
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
        
        ItemPostingResponseDto dto = new ItemPostingResponseDto(
            itemPosting.getId(),
            itemPosting.getTitle(),
            itemPosting.getDescription(),
            itemPosting.getDatePosted(),
            UniversityResponseDto.createDto(itemPosting.getUniversity()),
            PersonResponseDto.createDto(itemPosting.getPoster()),
            courses,
            itemPosting.isAvailable(),
            itemPosting.getPrice(),
            PersonResponseDto.createDto(itemPosting.getBuyer())
        );
        return dto;
    }
}
