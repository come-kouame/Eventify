package fr.univlyon1.m1if10.eventify_api;

import fr.univlyon1.m1if10.eventify_api.entity.Message;
import fr.univlyon1.m1if10.eventify_api.repository.MessageRepository;
import fr.univlyon1.m1if10.eventify_api.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageServiceTest {

    @Test
    void testGetAllMessages() {
        MessageRepository repository = Mockito.mock(MessageRepository.class);
        MessageService service = new MessageService(repository);

        Message m1 = new Message();
        m1.setId(1);
        m1.setContent("Message 1");

        Message m2 = new Message();
        m2.setId(2);
        m2.setContent("Message 2");

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Message> result = service.getAllMessages();

        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getContent());
        assertEquals("Message 2", result.get(1).getContent());
    }
}
