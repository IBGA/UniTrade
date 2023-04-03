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
import ca.mcgill.ecse428.unitrade.unitradebackend.security.CredentialsEncoder;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RoleService roleService;

    @Transactional
    public Person createPerson(
            String email,
            String username,
            String firstName,
            String lastName,
            String password,
            String profilePicture,
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

        // Validate that email and username are unique (Error -> 409)
        if (personRepository.findByEmail(email) != null) {
            throw new ServiceLayerException(HttpStatus.CONFLICT,
                    String.format("Email '%s' already exists in system", email));
        }

        if (personRepository.findByUsername(username) != null) {
            throw new ServiceLayerException(HttpStatus.CONFLICT,
                    String.format("Username '%s' already exists in system", username));
        }

        University university = null;

        if (universityId != null) {
            // Validate that university and courses exist (Error -> 404)
            university = universityRepository.findById(universityId).orElse(null);
            if (university == null) {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                        String.format("University with id '%d' not found", universityId));
            }
        }

        CredentialsEncoder encoder = new CredentialsEncoder();
        String encodedPassword = encoder.encode(password);

        if (encodedPassword == null || encodedPassword.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.INTERNAL_SERVER_ERROR, "Password encoding failed");
        }

        // Code reaches here -> No errors.
        // So create person and save to database
        Person person = new Person();
        person.setEmail(email);
        person.setUsername(username);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPassword(encodedPassword);
        person.setProfilePicture(profilePicture);
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
    public Boolean personExistsWithEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }

        Person person = personRepository.findByEmail(email);

        if (person == null) {
            return false;
        }

        return true;
    }

    @Transactional
    public Boolean personExistsWithUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Username cannot be null or empty");
        }

        Person person = personRepository.findByUsername(username);

        if (person == null) {
            return false;
        }

        return true;
    }

    @Transactional
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional
    public List<Person> getPersonsNonHelperFromUniversity(Long universityId) {
        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "University id cannot be null");
        }

        University university = universityRepository.findById(universityId).orElse(null);

        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id '%d' not found", universityId));
        }

        List<Person> persons = personRepository.findAllByUniversity(university);

        List<Person> nonHelpers = new ArrayList<>();

        for (Person person : persons) {
            if (!roleService.isAdministratorOrHelper(person.getId(), universityId)) {
                nonHelpers.add(person);
            }
        }

        return nonHelpers;
    }

    @Transactional
    public List<Person> getPersonsHelperFromUniversity(Long universityId) {
        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "University id cannot be null");
        }

        University university = universityRepository.findById(universityId).orElse(null);

        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id '%d' not found", universityId));
        }

        List<Person> persons = personRepository.findAllByUniversity(university);
        List<Person> helpers = new ArrayList<>();

        for (Person person : persons) {
            if (roleService.isHelper(person.getId(), universityId)) {
                helpers.add(person);
            }
        }

        return helpers;
    }

    @Transactional
    public List<String> getAllUsernames() {
        List<Person> persons = personRepository.findAll();
        List<String> usernames = new ArrayList<>();

        for (Person person : persons) {
            usernames.add(person.getUsername());
        }
        return usernames;
    }

    @Transactional
    public boolean isAdministratorOrHelperToSelfUniversity(Long authLong) {

        if (authLong == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Auth id cannot be null");
        }

        Person person = personRepository.findById(authLong).orElse(null);

        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' not found", authLong));
        }

        if (person.getUniversity() == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person is not associated with a university");
        }

        return roleService.isAdministratorOrHelper(authLong, person.getUniversity().getId());
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
