package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.CourseRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleService roleService;

    @Transactional
    public Course createCourse(
            String title,
            String codename,
            String description,
            Long universityId) {
        
        // Validate input synteax
        // Validate input syntax (Error -> 400)
        if (title == null || title.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Title cannot be null or empty");
        }

        if (codename == null || codename.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Codename cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Description name cannot be null or empty");
        }

        if (courseRepository.findByCodename(codename) != null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Course with codename already exists");
        }

        Course course = new Course();
        course.setTitle(title);
        course.setCodename(codename);
        course.setApproved(false);
        course.setDescription(description);

        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such university");
        course.setUniversity(university);
        return courseRepository.save(course);

    }

    @Transactional
    public Course getCourse(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");

        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such course");
        return course;
    }

    @Transactional
    public Course getCourse(String codename) {
        if (codename == null || codename.isEmpty()) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Codename cannot be null");

        Course course = courseRepository.findByCodename(codename);
        if (course == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such course");
        return course;
    }

    @Transactional
    public List<Course> getCourseByUniversity(Long id){
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        University university = universityRepository.findById(id).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("University with id %d not found", id));
        }
        
        List<Course> courses = courseRepository.findByUniversity(university);
        if (courses == null || courses.isEmpty()) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such course");
        return courses;
    }

    @Transactional
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public Course approve(Long id){
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such course");
        }

        course.setApproved(true);
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long authId, Long courseId) {

        if (courseId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        Person requester = personRepository.findById(authId).orElse(null);
        if (requester == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Person with id %d not found", authId));
        }

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Course with id %d not found", courseId));
        }
        Long universityId = course.getUniversity().getId();

        // Check if requester is an administrator (Error -> 403)
        if (!roleService.isAdministrator(authId, universityId)) {
            throw new ServiceLayerException(HttpStatus.FORBIDDEN,
                    String.format("Person with id %d is not an administrator of university with id %d", authId, universityId));
        }

        // Delete university
        courseRepository.delete(course);
    }
}
