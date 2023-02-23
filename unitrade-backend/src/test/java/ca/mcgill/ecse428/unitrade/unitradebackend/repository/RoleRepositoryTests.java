package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        roleRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRole() {
        Person moderator = new Person();
        Role role = new Role();

        ModerationRole modRole = Role.ModerationRole.ADMINISTRATOR;
        String username = "moderator";

        role.setRole(modRole);
        moderator.setUsername(username);
        role.setPerson(moderator);

        moderator = personRepository.save(moderator);
        role = roleRepository.save(role);

        Long roleID = role.getId();
        Long moderatorID = moderator.getId();

        moderator = personRepository.findById(moderatorID).orElse(null);
        role = roleRepository.findById(roleID).orElse(null);

        assertNotNull(role);
        assertNotNull(moderator);

        assertEquals(username, moderator.getUsername());
        assertEquals(modRole, role.getRole());
    }
}