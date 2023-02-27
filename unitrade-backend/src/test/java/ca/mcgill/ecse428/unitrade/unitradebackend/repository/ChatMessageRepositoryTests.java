package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ChatMessage;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
public class ChatMessageRepositoryTests {

    @Autowired ChatMessageRepository chatMessageRepository;

    @Autowired PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        chatMessageRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadChatMessage() {
        Person sender = new Person();
        Person receiver = new Person();

        String senderUsername = "sender";
        String receiverUsername = "receiver";
        String senderEmail = "sender@email.com";
        String receiverEmail = "receiver@email.com";

        sender.setUsername(senderUsername);
        sender.setEmail(senderEmail);
        receiver.setUsername(receiverUsername);
        receiver.setEmail(receiverEmail);
        Date date = new Date();

        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        String content = "Hello!";
        message.setContent(content);
        message.setSendDate(date);

        sender = personRepository.save(sender);
        receiver = personRepository.save(receiver);
        message = chatMessageRepository.save(message);

        Long messageID = message.getId();
        Long senderID = sender.getId();
        Long receiverID = receiver.getId();

        message = chatMessageRepository.findById(messageID).orElse(null);
        sender = personRepository.findById(senderID).orElse(null);
        receiver = personRepository.findById(receiverID).orElse(null);

        assertNotNull(message);
        assertNotNull(sender);
        assertNotNull(receiver);

        assertEquals(sender.getId(), message.getSender().getId());
        assertEquals(sender.getEmail(), message.getSender().getEmail());
        assertEquals(receiver.getId(), message.getReceiver().getId());
        assertEquals(receiver.getEmail(), message.getReceiver().getEmail());
        assertEquals(content, message.getContent());
        assertEquals(date, message.getSendDate());

    }
}