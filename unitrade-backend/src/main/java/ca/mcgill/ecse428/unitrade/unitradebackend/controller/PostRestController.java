package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PostRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PostResponseDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import ca.mcgill.ecse428.unitrade.unitradebackend.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Person created"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
    @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
    @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
public class PostRestController {
    @Autowired
    PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/post" })
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto body) {
        Post post = postService.createPost(
            body.getTitle(),
            body.getDescription(),
            body.getDatePosted(),
            body.getUniversityId(),
            body.getPosterId(),
            body.getCourseIds()
        );
        return new ResponseEntity<PostResponseDto>(PostResponseDto.createDto(post), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/post/{id}" })
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        return new ResponseEntity<PostResponseDto>(
            PostResponseDto.createDto(postService.getPost(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = { "/post" })
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponseDto> personResponseDtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            personResponseDtos.add(PostResponseDto.createDto(post));
        }

        return new ResponseEntity<List<PostResponseDto>>(
                personResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = { "/post/{id}" })
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<PostResponseDto>(HttpStatus.OK);
    }
}
