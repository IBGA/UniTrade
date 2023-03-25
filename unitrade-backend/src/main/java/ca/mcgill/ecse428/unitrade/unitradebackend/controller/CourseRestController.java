package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.CourseRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.CourseResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.CourseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "${allowed.origins}")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
@PreAuthorize("hasRole('USER')")
public class CourseRestController {

    @Autowired
    CourseService courseService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    @PostMapping(value = { "/course" })
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto body) {
        Course course = courseService.createCourse(
                body.getTitle(),
                body.getCodename(),
                body.getDescription(),
                body.getUniversityId());
        return new ResponseEntity<CourseResponseDto>(CourseResponseDto.createDto(course), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/course/{id}" })
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable("id") Long id) {
        return new ResponseEntity<CourseResponseDto>(
                CourseResponseDto.createDto(courseService.getCourse(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/course/{codename}" })
    public ResponseEntity<CourseResponseDto> getCourseByCodename(@PathVariable("codename") String codename) {
        return new ResponseEntity<CourseResponseDto>(
                CourseResponseDto.createDto(courseService.getCourse(codename)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/course" })
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseResponseDto> courseResponseDtos = new ArrayList<CourseResponseDto>();
        for (Course course : courses) {
            courseResponseDtos.add(CourseResponseDto.createDto(course));
        }

        return new ResponseEntity<List<CourseResponseDto>>(
                courseResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = { "/course/{id}/approve" })
    public ResponseEntity<CourseResponseDto> approveCourse(@PathVariable("id") Long id) {
        return new ResponseEntity<CourseResponseDto>(CourseResponseDto.createDto(courseService.approve(id)),
                HttpStatus.OK);
    }
}
