package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Reaction;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{
    public List<Reaction> findByPost(Post post);
}
