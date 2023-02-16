package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

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
public class PersonRepositoryTests {

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPerson() {
        Person person = new Person();

        String username = "username";
        String password = "password";
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        String picture = "img.png";
        Boolean enabled = true;

        person.setUsername(username);
        person.setPassword(password);
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setProfilePicture(picture);
        person.setEnabled(enabled);

        person = personRepository.save(person);

        long personID = person.getId();

        person = personRepository.findById(personID).orElse(null);

        assertNotNull(person);

        assertEquals(username, person.getUsername());
        assertEquals(password, person.getPassword());
        assertEquals(email, person.getEmail());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(picture, person.getProfilePicture());
        assertEquals(enabled, person.isEnabled());
    }
}
