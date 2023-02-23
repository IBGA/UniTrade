package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ModerationTier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ModerationTier.ModerationRole;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ModerationTierRepositoryTests {

    @Autowired
    ModerationTierRepository moderationTierRepository;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        moderationTierRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadModerationTier() {
        Person moderator = new Person();
        ModerationTier moderationTier = new ModerationTier();

        ModerationRole role = ModerationTier.ModerationRole.ADMINISTRATOR;
        String username = "moderator";

        moderationTier.setRole(role);
        moderator.setUsername(username);
        moderationTier.setPerson(moderator);

        moderator = personRepository.save(moderator);
        moderationTier = moderationTierRepository.save(moderationTier);

        Long moderationTierID = moderationTier.getId();
        Long moderatorID = moderator.getId();

        moderator = personRepository.findById(moderatorID).orElse(null);
        moderationTier = moderationTierRepository.findById(moderationTierID).orElse(null);

        assertNotNull(moderationTier);
        assertNotNull(moderator);

        assertEquals(username, moderator.getUsername());
        assertEquals(role, moderationTier.getRole());
    }
}