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

        sender.setUsername(senderUsername);
        receiver.setUsername(receiverUsername);
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

        long messageID = message.getId();
        long senderID = sender.getId();
        long receiverID = receiver.getId();

        message = chatMessageRepository.findById(messageID).orElse(null);
        sender = personRepository.findById(senderID).orElse(null);
        receiver = personRepository.findById(receiverID).orElse(null);

        assertNotNull(message);
        assertNotNull(sender);
        assertNotNull(receiver);

        assertEquals(sender.getId(), message.getSender().getId());
        assertEquals(receiver.getId(), message.getReceiver().getId());
        assertEquals(content, message.getContent());
        assertEquals(date, message.getSendDate());
        
    }
}