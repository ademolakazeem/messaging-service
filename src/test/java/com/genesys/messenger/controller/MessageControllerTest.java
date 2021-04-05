package com.genesys.messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.messenger.model.Message;
import com.genesys.messenger.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private ObjectMapper objectMapper = new ObjectMapper();


    private Message sender;
    private Message recipient;

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
    }

    @Test
    public void givenMessages_whenGetAllMessages_thenReturnJsonArray() throws Exception {
        given(messageService.findAll()).willReturn(Arrays.asList(sender));

        mockMvc.perform(get("/messages/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].senderName", is(sender.getSenderName())));
    }

    @Test
    public void saveMessage_itShouldReturnStatusOk() throws Exception {
        given(messageService.saveMessage(sender)).willReturn(sender);

        String jsonString = objectMapper.writeValueAsString(sender);

        mockMvc.perform(post("/messages/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }
}
