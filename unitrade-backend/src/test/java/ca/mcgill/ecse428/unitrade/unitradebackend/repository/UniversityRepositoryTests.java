package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class UniversityRepositoryTests {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @AfterEach
    public void clearDatabase() {
        universityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUniversity() {
        University university = new University();
        Person moderator = new Person();
        Role role = new Role();

        Role.ModerationRole modRole = Role.ModerationRole.ADMINISTRATOR;
        moderator.setUsername("moderator");
        role.setRole(modRole);
        role.setPerson(moderator);

        String name = "McGill University";
        String city = "McGill";
        String description = "A university in Montreal, Canada";

        university.setName(name);
        university.setCity(city);
        university.setDescription(description);

        moderator = personRepository.save(moderator);
        role = roleRepository.save(role);
        university = universityRepository.save(university);

        Long universityID = university.getId();

        university = universityRepository.findById(universityID).orElse(null);

        assertNotNull(university);
        assertEquals(name, university.getName());
        assertEquals(city, university.getCity());
        assertEquals(description, university.getDescription());
    }
}
