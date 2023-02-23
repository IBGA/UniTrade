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
public class UniversityService {
    @Autowired
    UniversityRepository universityRepository;

    @Transactional
    public University createUniversity(
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
        return universityRepository.save(university);
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
    public University getUniversity(String name, String city) {
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
            Long id,
            String name,
            String city,
            String description) {
        // Validate input syntax (Error -> 400)
        if (id == null) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        if (name == null || name.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }

        // Check if university exists (Error -> 404)
        University university = universityRepository.findById(id).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("University with id %d not found", id));
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
    
    
}

//Use this for reference
/*package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class University {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String description;
    @OneToMany private List<Role> moderation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getModeration() {
        return moderation;
    }

    public void setModeration(List<Role> moderation) {
        this.moderation = moderation;
    }

}
*/