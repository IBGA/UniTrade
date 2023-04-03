package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;

@Service
public class UniversityService {
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleService roleService;

    @Transactional
    public University createUniversity(
            Long requesterId,
            String name,
            String city,
            String description) {

        // Validate input syntax (Error -> 400)
        if (name == null || name.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }

        // Check if university already exists (Error -> 409)
        University university = universityRepository.findByNameAndCity(name, city);
        if (university != null) {
            throw new ServiceLayerException(HttpStatus.CONFLICT,
                    String.format("University with name %s and city %s already exists", name, city));
        }

        // Create university
        university = new University();
        university.setName(name);
        university.setCity(city);
        university.setDescription(description);
        University savedUniversity = universityRepository.save(university);

        //if (roleService.getSelfRoleUniversityId(requesterId) != 0) {
        //    throw new ServiceLayerException(HttpStatus.FORBIDDEN,
        //            String.format("Person with id %d is already an administrator of university with id %d", requesterId, roleService.getSelfRoleUniversityId(requesterId)));
        //}

        // Create role
        if (requesterId != null) {
            if (personRepository.findById(requesterId).orElse(null) == null) {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                        String.format("Person with id %d not found", requesterId));
            }
            roleService.createRole(null, requesterId, savedUniversity.getId(), ModerationRole.ADMINISTRATOR);
        }

        return savedUniversity;
    }

    @Transactional
    public University getUniversity(Long id) {
        // Validate input syntax (Error -> 400)
        if (id == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        // Check if university exists (Error -> 404)
        University university = universityRepository.findById(id).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id %d not found", id));
        }

        return university;
    }

    @Transactional
    public University getUniversity(String city, String name) {
        // Validate input syntax (Error -> 400)
        if (name == null || name.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }

        if (city == null || city.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "City cannot be null or empty");
        }

        // Check if university exists (Error -> 404)
        University university = universityRepository.findByNameAndCity(name, city);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with name %s and city %s not found", name, city));
        }

        return university;
    }

    @Transactional
    public University updateUniversity(
            Long authId,
            Long universityId,
            String name,
            String city,
            String description) {

        Person requester = personRepository.findById(authId).orElse(null);
        if (requester == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Person with id %d not found", authId));
        }

        // Check if requester is an administrator (Error -> 403)
        if (!roleService.isAdministrator(authId, universityId)) {
            throw new ServiceLayerException(HttpStatus.FORBIDDEN,
                    String.format("Person with id %d is not an administrator of university with id %d", authId, universityId));
        }

        // Validate input syntax (Error -> 400)
        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        if (name == null || name.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }

        // Check if university exists (Error -> 404)
        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id %d not found", universityId));
        }

        // Update university
        university.setName(name);
        university.setCity(city);
        university.setDescription(description);
        return universityRepository.save(university);
    }

    @Transactional
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    @Transactional
    public void deleteUniversity(Long authId, Long universityId) {

        Person requester = personRepository.findById(authId).orElse(null);
        if (requester == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("Person with id %d not found", authId));
        }

        // Check if requester is an administrator (Error -> 403)
        if (!roleService.isAdministrator(authId, universityId)) {
            throw new ServiceLayerException(HttpStatus.FORBIDDEN,
                    String.format("Person with id %d is not an administrator of university with id %d", authId, universityId));
        }

        // Validate input syntax (Error -> 400)
        if (universityId == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        // Check if university exists (Error -> 404)
        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND,
                    String.format("University with id %d not found", universityId));
        }

        // Delete university
        universityRepository.delete(university);
    }

}