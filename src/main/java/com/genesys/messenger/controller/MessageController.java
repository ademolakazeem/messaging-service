package com.genesys.messenger.controller;


import com.genesys.messenger.exception.UserBadRequestException;
import com.genesys.messenger.model.Message;
import com.genesys.messenger.model.MessageNotification;
import com.genesys.messenger.service.MessageService;
import com.genesys.messenger.service.MessagingRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessagingRoomService messagingRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        String chatId = messagingRoomService
                .getChatId(message.getSenderId(), message.getRecipientId());
        message.setChatId(chatId);

        Message saved = messageService.saveMessage(message);
        messagingTemplate.convertAndSendToUser(
                message.getRecipientId(),"/queue/messages",
                new MessageNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        try {
            processMessage(message);
                return ResponseEntity.created(new URI("/messages/" + message.getId())).build();
        } catch (Exception e) {
            throw new UserBadRequestException(e.getMessage());
        }
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<?> editMessage(@PathVariable String id, @RequestBody Message message) {
        if(id.equals(message.getId())){
            if(messageService.editMessage(id, message).getId() != null ) {
                return ResponseEntity.accepted().build();
            }
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Looks like you're trying to Update a different message");
        }
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(messageService.findById(id));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/chat")
    public ResponseEntity<?> findMessagesBySenderAndRecipient ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        List<Message> messages = messageService.findMessagesBySenderIdAndRecipientId(senderId, recipientId);
        if(messages == null || messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/messages")
    public ResponseEntity<?> findAllMessages () {
        List<Message> messages = messageService.findAll();
        if(messages == null || messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/messages/{senderId}/sender")
    public ResponseEntity<?> findMessagesBySenderId( @PathVariable String senderId) {
        List<Message> messages = messageService.findMessagesBySenderId(senderId);
        if(messages == null || messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/messages/{recipientId}/recipient")
    public ResponseEntity<?> findMessagesByRecipientId ( @PathVariable String recipientId) {
        List<Message> messages = messageService.findMessagesByRecipientId(recipientId);
        if(messages == null || messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        if(messageService.findById(id).getId() != null) {
            messageService.deleteMessage(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/messages/{chatId}/chat")
    public ResponseEntity<?> deleteChat(@PathVariable String chatId) {
        if(!messageService.findMessagesByChatId(chatId).isEmpty()) {
            messageService.deleteChat(chatId);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}