package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Reaction;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ReactionRepositoryTests {

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void clearDatabase() {
        reactionRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadReaction() {
        Person person = new Person();
        Post post = new Post();
        Reaction reaction = new Reaction();

        String username = "person";
        String title = "Hello!";
        String emoji = "👍";

        person.setUsername(username);
        post.setTitle(title);
        reaction.setPost(post);
        reaction.setReactors(List.of(person));
        reaction.setEmoji(emoji);

        person = personRepository.save(person);
        post = postRepository.save(post);
        reaction = reactionRepository.save(reaction);

        Long reactionID = reaction.getId();
        
        reaction = reactionRepository.findById(reactionID).orElse(null);

        assertNotNull(reaction);
        assertEquals(emoji, reaction.getEmoji());
        assertEquals(title, reaction.getPost().getTitle());
        assertEquals(username, reaction.getReactors().get(0).getUsername());
    }
}