package com.genesys.messenger.service;

import com.genesys.messenger.model.Message;
import com.genesys.messenger.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @MockBean
    MessageRepository messageRepository;

    private Message sender;
    private Message recipient;

    private final List<Message> messages = new ArrayList<>();

    @Before
    public void setUp(){
        sender = new Message();
        sender.setContent("Hi how are you doing?");
        sender.setSenderName("Diamond");
        sender.setSenderId("6069d551af022555e6410178");
        sender.setRecipientName("Pam");
        sender.setRecipientId("6069d56faf022555e6410179");
        sender.setTimestamp(new Date());

        recipient = new Message();
        recipient.setContent("Hello, I'm doing ok, hope you are too");
        recipient.setSenderName("Pam");
        recipient.setSenderId("6069d56faf022555e6410179");
        recipient.setRecipientName("Diamond");
        recipient.setRecipientId("6069d551af022555e6410178");
        recipient.setTimestamp(new Date());

        messages.add(sender);
        messages.add(recipient);

        Mockito.when(messageRepository.findAll()).thenReturn(messages);


    }
   @Test
   public void testSaveMessage_thenMessagesShouldBeReturned(){
        Message message = messageService.saveMessage(sender);
       assertNotNull(message);
       assertEquals(sender.getSenderName(), message.getSenderName());
       assertEquals(sender.getSenderId(), message.getSenderId());
       assertEquals(sender.getRecipientId(), message.getRecipientId());
       assertEquals(sender.getContent(), sender.getContent());
   }

    @Test
    public void testFindAll_thenMessageListShouldBeReturned() {
        List<Message> messages = messageService.findAll();

        assertNotNull(messages);
        assertEquals(2, messages.size());
    }



}
