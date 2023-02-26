package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.ItemPostingRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.ItemPostingResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.ItemPostingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "${allowed.origins}")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
public class ItemPostingRestController {
    @Autowired
    ItemPostingService itemPostingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/itemposting" })
    public ResponseEntity<ItemPostingResponseDto> createItemPosting(@RequestBody ItemPostingRequestDto body) {
        ItemPosting itemPosting = itemPostingService.createItemPosting(
                body.getTitle(),
                body.getDescription(),
                body.getDatePosted(),
                body.getUniversityId(),
                body.getPosterId(),
                body.getCourseIds(),
                body.isAvailable(),
                body.getPrice(),
                body.getBuyerId());
        return new ResponseEntity<ItemPostingResponseDto>(ItemPostingResponseDto.createDto(itemPosting),
                HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/itemposting/{id}" })
    public ResponseEntity<ItemPostingResponseDto> getItemPosting(@PathVariable("id") Long id) {
        return new ResponseEntity<ItemPostingResponseDto>(
                ItemPostingResponseDto.createDto(itemPostingService.getItemPosting(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/itemposting/university/{id}" })
    public ResponseEntity<List<ItemPostingResponseDto>> getAllItemPostingsByUniversity(@PathVariable("id") Long id) {
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostingsByUniversity(id);
        List<ItemPostingResponseDto> itemPostingResponseDtos = new ArrayList<ItemPostingResponseDto>();
        for (ItemPosting itemPost : itemPostings) {
            itemPostingResponseDtos.add(ItemPostingResponseDto.createDto(itemPost));
        }

        return new ResponseEntity<List<ItemPostingResponseDto>>(
                itemPostingResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/itemposting/course/{id}" })
    public ResponseEntity<List<ItemPostingResponseDto>> getAllItemPostingsByCourse(@PathVariable("id") Long id) {
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostingsByCourse(id);
        List<ItemPostingResponseDto> itemPostingResponseDtos = new ArrayList<ItemPostingResponseDto>();
        for (ItemPosting itemPost : itemPostings) {
            itemPostingResponseDtos.add(ItemPostingResponseDto.createDto(itemPost));
        }

        return new ResponseEntity<List<ItemPostingResponseDto>>(
                itemPostingResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/itemposting/university/{universityId}/course/{courseId}" })
    public ResponseEntity<List<ItemPostingResponseDto>> getAllItemPostingsByCourse(
            @PathVariable("universityId") Long universityId, @PathVariable("courseId") Long courseId) {
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostingsByUniversityAndCourse(universityId,
                courseId);
        List<ItemPostingResponseDto> itemPostingResponseDtos = new ArrayList<ItemPostingResponseDto>();
        for (ItemPosting itemPost : itemPostings) {
            itemPostingResponseDtos.add(ItemPostingResponseDto.createDto(itemPost));
        }

        return new ResponseEntity<List<ItemPostingResponseDto>>(
                itemPostingResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/itemposting" })
    public ResponseEntity<List<ItemPostingResponseDto>> getAllItemPostings() {
        List<ItemPosting> itemPostings = itemPostingService.getAllItemPostings();
        List<ItemPostingResponseDto> itemPostingResponseDtos = new ArrayList<ItemPostingResponseDto>();
        for (ItemPosting itemPost : itemPostings) {
            itemPostingResponseDtos.add(ItemPostingResponseDto.createDto(itemPost));
        }

        return new ResponseEntity<List<ItemPostingResponseDto>>(
                itemPostingResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = { "/itemposting/{id}" })
    public ResponseEntity<ItemPostingResponseDto> deleteItemPosting(@PathVariable("id") Long id) {
        itemPostingService.deleteItemPosting(id);
        return new ResponseEntity<ItemPostingResponseDto>(HttpStatus.OK);
    }
}
