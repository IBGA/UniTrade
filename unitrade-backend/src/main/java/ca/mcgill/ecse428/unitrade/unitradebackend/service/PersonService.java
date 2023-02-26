package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.ArrayList;
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
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    CourseRepository courseRepository;

    @Transactional
    public Person createPerson(
            String email,
            String username,
            String firstName,
            String lastName,
            String password,
            String profilePicture,
            List<Long> enrolledCoursesIds,
            Long universityId) {

        // Validate input syntax (Error -> 400)
        if (email == null || email.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }

        if (username == null || username.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Username cannot be null or empty");
        }

        if (firstName == null || firstName.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "First name cannot be null or empty");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Last name cannot be null or empty");
        }

        if (password == null || password.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        }

        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "University id cannot be null");
        }

        // Validate that email and username are unique (Error -> 409)
        if (personRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.CONFLICT,
                    String.format("Email '%s' already exists in system", email));
        }

        if (personRepository.findByUsername(username) != null) {
            throw new ServiceLayerException(HttpStatus.CONFLICT,
                    String.format("Username '%s' already exists in system", username));
        }

        // Validate that university and courses exist (Error -> 404)
        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id '%d' not found", universityId));
        }

        List<Course> enrolledCourses = new ArrayList<Course>();
        for (Long courseId : enrolledCoursesIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course == null) {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                        String.format("Course with id '%d' not found", courseId));
            }
            enrolledCourses.add(course);
        }

        // Code reaches here -> No errors.
        // So create person and save to database
        Person person = new Person();
        person.setEmail(email);
        person.setUsername(username);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPassword(password);
        person.setProfilePicture(profilePicture);

        person.setEnrolledCourses(enrolledCourses);
        person.setUniversity(university);
        return personRepository.save(person);
    }

    @Transactional
    public Person getPerson(Long id) {
        if (id == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        }

        return person;
    }

    @Transactional
    public Person getPersonByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email cannot be empty");
        }

        Person person = personRepository.findByEmail(email);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Person with email '%s' not found", email));
        }

        return person;
    }

    @Transactional
    public Person getPersonByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Username cannot be empty");
        }

        Person person = personRepository.findByUsername(username);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Person with username '%s' not found", username));
        }

        return person;
    }

    @Transactional
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional
    public Person updatePersonInformation(
            Long id,
            String firstName,
            String lastName,
            String profilePicture) {
        Person person = personRepository.findById(id).orElse(null);

        if (firstName == null || firstName.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "First name cannot be null or empty");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Last name cannot be null or empty");
        }

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        }

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setProfilePicture(profilePicture);

        return personRepository.save(person);
    }

    @Transactional
    public Person updatePersonPassword(Long id, String password) {
        Person person = personRepository.findById(id).orElse(null);

        if (password == null || password.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        }

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        }

        person.setPassword(password);

        return personRepository.save(person);
    }

    @Transactional
    public Person updatePersonCurrentUniversity(Long id, Long universityId) {

        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        }

        University university = universityRepository.findById(universityId).orElse(null);

        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id '%d' not found", universityId));
        }

        person.setUniversity(university);
        return personRepository.save(person);
    }

    @Transactional
    public Person updatePersonEnrolledCourses(Long id, List<Long> enrolledCoursesIds) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        }

        List<Course> enrolledCourses = new ArrayList<Course>();
        for (Long courseId : enrolledCoursesIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course == null) {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                        String.format("Course with id '%d' not found", courseId));
            }
            enrolledCourses.add(course);
        }

        person.setEnrolledCourses(enrolledCourses);
        return personRepository.save(person);
    }

    @Transactional
    public void deletePerson(Long id) {
        if (id == null) throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        
        Person person = personRepository.findById(id).orElse(null);
        if (person == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", id));
        personRepository.delete(person);
    }
}
