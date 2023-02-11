package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ChatMessage;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    public List<ChatMessage> findBySenderAndReceiver(Person sender, Person receiver);
}
