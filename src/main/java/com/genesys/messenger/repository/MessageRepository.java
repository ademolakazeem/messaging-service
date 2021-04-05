package com.genesys.messenger.repository;

import com.genesys.messenger.model.Message;
import com.genesys.messenger.model.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository
        extends MongoRepository<Message, String> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<Message> findByChatId(String chatId);
    List<Message> findMessagesBySenderIdAndAndRecipientId(String senderId, String recipientId);
    List<Message> findMessagesBySenderId(String senderId);
    List<Message> findMessagesByRecipientId(String recipientId);
}