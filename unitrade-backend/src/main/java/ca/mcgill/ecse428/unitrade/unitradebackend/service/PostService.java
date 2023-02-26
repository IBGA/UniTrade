package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PostRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@Service
public class PostService {
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PostRepository postRepository;

    @Transactional
    public Post createPost(
            String title,
            String description,
            Date datePosted,
            Long universityId,
            Long posterId,
            List<Long> courseIds ) {
        
        // Validate input syntax (Error -> 400)
        if (title == null || title.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Title cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }

        if (datePosted == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Date posted cannot be null");
        }

        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "UniversityId cannot be null");
        }

        if (posterId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "PosterId cannot be null");
        }

        if (courseIds == null || courseIds.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "CourseIds cannot be null or empty");
        }
        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("University with id '%d' not found", universityId));

        Person poster = personRepository.findById(posterId).orElse(null);
        if (poster == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Poster with id '%d' not found", posterId));

        List<Course> courses = new ArrayList<Course>();
        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Course with id '%d' not found", courseId));
            courses.add(course);
        }

        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setDatePosted(datePosted);
        post.setUniversity(university);
        post.setPoster(poster);
        post.setCourses(courses);

        return postRepository.save(post);
    }

    @Transactional
    public Post getPost(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Post with id '%d' not found", id));
        
        return post;
    }

    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void deletePost(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");

        Post post = postRepository.findById(id).orElse(null);
        if (post == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Post with id '%d' not found", id));
        postRepository.delete(post);
    }
}
