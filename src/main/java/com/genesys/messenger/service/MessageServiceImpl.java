package com.genesys.messenger.service;


import com.genesys.messenger.exception.DataNotFoundException;
import com.genesys.messenger.model.Message;
import com.genesys.messenger.model.MessageStatus;
import com.genesys.messenger.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessagingRoomServiceImpl messagingRoomServiceImpl;
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Message saveMessage(Message message) {
        message.setStatus(MessageStatus.RECEIVED);
        messageRepository.save(message);
        return message;
    }

    @Override
    public Message editMessage(String id, Message message) {
        Optional<Message> messageRepositoryById = messageRepository.findById(id);
        if(messageRepositoryById != null && !messageRepositoryById.equals(Optional.empty())) {
            Query query = new Query(
                    Criteria.where("id").is(id));
            Update messageUpdate = new Update();
            messageUpdate.set("content", message.getContent());
            messageUpdate.set("timestamp", message.getTimestamp());
            messageUpdate.set("status", MessageStatus.RECEIVED);
            mongoOperations.updateMulti(query, messageUpdate, Message.class);
            return message;
        }
        throw  new DataNotFoundException("Sorry, we cannot find message with id: " + id);
    }
    @Override
    public void deleteMessage(String id) {
        Optional<Message> message = messageRepository.findById(id);
        if(message != null && !message.equals(Optional.empty())) {
            Query query = new Query(
                    Criteria
                            .where("id").is(id));
            mongoOperations.remove(query, "message");
        }
        else {
            throw  new DataNotFoundException("Sorry, we cannot find message with id: " + id);
        }
    }

    @Override
    public void deleteChat(String chatId) {
            List<Message> message = messageRepository.findByChatId(chatId);
            if(message != null && !message.equals(Optional.empty())) {
                Query query = new Query(
                        Criteria
                                .where("chatId").is(chatId));
                mongoOperations.remove(query, "message");
                mongoOperations.remove(query, "messagingRoom");
            }
            else{
                throw  new DataNotFoundException("Sorry, we cannot find message with id: " + chatId);
            }
    }

    @Override
    public long countNewMessages(String senderId, String recipientId) {
        return messageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    @Override
    public List<Message> findChatMessages(String senderId, String recipientId) {
        String chatId = messagingRoomServiceImpl.getChatId(senderId, recipientId);
        List<Message> messages = messageRepository.findByChatId(chatId);
        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    public Message findById(String id) {
        return messageRepository
                .findById(id)
                .map(message -> {
                    message.setStatus(MessageStatus.DELIVERED);
                    return messageRepository.save(message);
                })
                .orElseThrow(() ->
                        new DataNotFoundException("Sorry, we cannot find message (" + id + ")"));
    }


    @Override
    public void updateStatuses(String senderId, String recipientId, MessageStatus messageStatus) {
        Query query = new Query(
                Criteria
                        .where("senderId").is(senderId)
                        .and("recipientId").is(recipientId));
        Update update = Update.update("status", messageStatus);
        mongoOperations.updateMulti(query, update, Message.class);

    }
    @Override
    public List<Message> findMessagesByChatId(String chatId){
        return messageRepository.findByChatId(chatId);
    }

    @Override
    public List<Message> findMessagesBySenderIdAndRecipientId(String senderId, String recipientId) {
       return messageRepository.findMessagesBySenderIdAndAndRecipientId(senderId, recipientId);
    }

    @Override
    public List<Message> findMessagesBySenderId(String senderId) {
        return messageRepository.findMessagesBySenderId(senderId);
    }

    @Override
    public List<Message> findMessagesByRecipientId(String recipientId) {
        return messageRepository.findMessagesByRecipientId(recipientId);
    }

    @Override
    public List<Message> findAll(){
        return messageRepository.findAll();
    }
}