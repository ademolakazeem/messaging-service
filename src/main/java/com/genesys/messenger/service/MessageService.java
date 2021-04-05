package com.genesys.messenger.service;

import com.genesys.messenger.model.Message;
import com.genesys.messenger.model.MessageStatus;

import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    Message editMessage(String id, Message message);
    long countNewMessages(String senderId, String recipientId);
    List<Message> findChatMessages(String senderId, String recipientId);
    Message findById(String id);
    void updateStatuses(String senderId, String recipientId, MessageStatus status);
    void deleteMessage(String id);
    void deleteChat(String chatId);
    List<Message> findMessagesByChatId(String chatId);
    List<Message> findMessagesBySenderIdAndRecipientId (String senderId, String recipientId);
    List<Message> findMessagesBySenderId(String senderId);
    List<Message> findMessagesByRecipientId(String recipientId);
    List<Message> findAll();
}
