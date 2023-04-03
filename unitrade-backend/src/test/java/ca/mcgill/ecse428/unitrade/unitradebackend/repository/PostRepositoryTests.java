package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Unipost;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        postRepository.deleteAll();
    }
 
    @Test
    public void testPersistAndLoadItemPosting(){
        ItemPosting itemPosting = new ItemPosting();
        University university = new University();
        Person poster = new Person();
        Person buyer = new Person();

        String uniName = "McGill";
        String title = "Item For Sale";
        String description = "This is a description";
        String posterName = "poster";
        String posterEmail = "poster@email.com";
        String buyerEmail = "buyer@email.com";
        String buyerName = "buyer";
        Double price = 10.0;
        Boolean isAvailable = false;

        university.setName(uniName);
        poster.setUsername(posterName);
        poster.setEmail(posterEmail);
        buyer.setEmail(buyerEmail);
        buyer.setUsername(buyerName);
        itemPosting.setTitle(title);
        itemPosting.setDescription(description);
        itemPosting.setPoster(poster);
        itemPosting.setBuyer(buyer);
        itemPosting.setAvailable(isAvailable);
        itemPosting.setUniversity(university);
        itemPosting.setPrice(price);

        university = universityRepository.save(university);
        poster = personRepository.save(poster);

        buyer = personRepository.save(buyer);
        itemPosting = postRepository.save(itemPosting);

        Long itemPostingID = itemPosting.getId();

        itemPosting = (ItemPosting) postRepository.findById(itemPostingID).orElse(null);

        assertNotNull(itemPosting);

        assertEquals(title, itemPosting.getTitle());
        assertEquals(description, itemPosting.getDescription());
        assertEquals(price, itemPosting.getPrice());
        assertEquals(posterName, itemPosting.getPoster().getUsername());
        assertEquals(posterEmail, itemPosting.getPoster().getEmail());
        assertEquals(buyerName, itemPosting.getBuyer().getUsername());
        assertEquals(buyerEmail, itemPosting.getBuyer().getEmail());
        assertEquals(isAvailable, itemPosting.isAvailable());
        assertEquals(uniName, itemPosting.getUniversity().getName());
    }

    @Test
    public void testPersistAndLoadUnipost(){
        Unipost unipost = new Unipost();
        University university = new University();
        Person poster = new Person();

        String uniName = "McGill";
        String title = "Item For Sale";
        String description = "This is a description";
        String posterName = "poster";

        university.setName(uniName);
        poster.setUsername(posterName);
        unipost.setTitle(title);
        unipost.setDescription(description);
        unipost.setPoster(poster);
        unipost.setUniversity(university);

        university = universityRepository.save(university);
        poster = personRepository.save(poster);
        unipost = postRepository.save(unipost);

        Long unipostID = unipost.getId();
        
        unipost = (Unipost) postRepository.findById(unipostID).orElse(null);

        assertNotNull(unipost);

        assertEquals(title, unipost.getTitle());
        assertEquals(description, unipost.getDescription());
        assertEquals(posterName, unipost.getPoster().getUsername());
        assertEquals(uniName, unipost.getUniversity().getName());
    }



    @Test
    public void testPersistAndLoadPost(){
        Post post = new Post();
        University university = new University();
        Person poster = new Person();

        String uniName = "McGill";
        String title = "Item For Sale";
        String description = "This is a description";
        String posterName = "poster";

        university.setName(uniName);
        poster.setUsername(posterName);
        post.setTitle(title);
        post.setDescription(description);
        post.setPoster(poster);
        post.setUniversity(university);

        university = universityRepository.save(university);
        poster = personRepository.save(poster);
        post = postRepository.save(post);

        Long postID = post.getId();
        
        post = postRepository.findById(postID).orElse(null);

        assertNotNull(post);

        assertEquals(title, post.getTitle());
        assertEquals(description, post.getDescription());
        assertEquals(posterName, post.getPoster().getUsername());
        assertEquals(uniName, post.getUniversity().getName());
    }
    
}
