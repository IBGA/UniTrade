package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ModerationTier;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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
    ModerationTierRepository moderationTierRepository;

    @AfterEach
    public void clearDatabase() {
        universityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUniversity() {
        University university = new University();
        List<ModerationTier> moderators;
        Person moderator = new Person();
        ModerationTier moderationTier = new ModerationTier();

        ModerationTier.ModerationRole role = ModerationTier.ModerationRole.ADMINISTRATOR;
        moderator.setUsername("moderator");
        moderationTier.setRole(role);
        moderationTier.setPerson(moderator);
        moderators = List.of(moderationTier);

        String name = "McGill University";
        String codename = "McGill";
        String description = "A university in Montreal, Canada";

        university.setName(name);
        university.setCodename(codename);
        university.setDescription(description);
        university.setModeration(moderators);
        
        moderator = personRepository.save(moderator);
        moderationTier = moderationTierRepository.save(moderationTier);
        university = universityRepository.save(university);

        long universityID = university.getId();

        university = universityRepository.findById(universityID).orElse(null);

        assertNotNull(university);
        assertEquals(name, university.getName());
        assertEquals(codename, university.getCodename());
        assertEquals(description, university.getDescription());
        assertEquals(moderators, university.getModeration());

    }
}
