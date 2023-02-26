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
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.ItemPostingRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@Service
public class ItemPostingService {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ItemPostingRepository itemPostingRepository;
    
    @Transactional
    public ItemPosting createItemPosting(
            String title,
            String description,
            Date datePosted,
            Long universityId,
            Long posterId,
            List<Long> courseIds,
            boolean isAvailable,
            Double price,
            Long buyerId) {
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

        if (courseIds == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "CourseIds cannot be null");
        }

        if (price == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Price cannot be null");
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

        ItemPosting itemPosting = new ItemPosting();
        itemPosting.setTitle(title);
        itemPosting.setDescription(description);
        itemPosting.setDatePosted(datePosted);
        itemPosting.setUniversity(university);
        itemPosting.setPoster(poster);
        itemPosting.setCourses(courses);
        itemPosting.setAvailable(isAvailable);
        itemPosting.setPrice(price);

        if (buyerId != null) {
            Person buyer = personRepository.findById(buyerId).orElse(null);
            if (buyer == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Buyer with id '%d' not found", buyerId));
            itemPosting.setBuyer(buyer);
        }

        return itemPostingRepository.save(itemPosting);
    }

    @Transactional
    public ItemPosting getItemPosting(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        
        ItemPosting itemPosting = itemPostingRepository.findById(id).orElse(null);
        if (itemPosting == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Item posting with id '%d' not found", id));
        
        return itemPosting;
    }

    @Transactional
    public List<ItemPosting> getAllItemPostingsByUniversity(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        
        University university = universityRepository.findById(id).orElse(null);
        if (university == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("University with id '%d' not found", id));

        List<ItemPosting> itemPostings = itemPostingRepository.findByUniversity(university);
        if (itemPostings == null || itemPostings.isEmpty()) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Item posting with unniversity id '%d' not found", id));
        
        return itemPostings;
    }

    @Transactional
    public List<ItemPosting> getAllItemPostingsByCourse(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Course with id '%d' not found", id));

        List<ItemPosting> courses = itemPostingRepository.findByCourses(course);
        if (courses == null || courses.isEmpty()) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Item posting with course id '%d' not found", id));
        
        return courses;
    }

    @Transactional
    public List<ItemPosting> getAllItemPostingsByUniversityAndCourse(Long universityId, Long courseId) {
        if (universityId == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        if (courseId == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");

        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("University with id '%d' not found", universityId));
        
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Course with id '%d' not found", courseId));

        List<ItemPosting> courses = itemPostingRepository.findByUniversityAndCourses(university, course);
        if (courses == null || courses.isEmpty()) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Item posting with university id '%d' and course id '%d' not found", universityId, courseId));
        
        return courses;
    }

    @Transactional
    public List<ItemPosting> getAllItemPostings() {
        return itemPostingRepository.findAll();
    }

    @Transactional
    public void deleteItemPosting(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");

        ItemPosting itemPosting = itemPostingRepository.findById(id).orElse(null);
        if (itemPosting == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Item posting with id '%d' not found", id));
        itemPostingRepository.delete(itemPosting);
    }
}
