package com.genesys.messenger.service;

import com.genesys.messenger.model.MessagingRoom;
import com.genesys.messenger.repository.MessagingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingRoomServiceImpl implements MessagingRoomService{

    @Autowired
    private
    MessagingRoomRepository messagingRoomRepository;

    @Override
    public String getChatId(
            String senderId, String recipientId) {

        MessagingRoom messagingRoom = messagingRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (messagingRoom != null) {
            return messagingRoom.getChatId();
        }
        return buildAndSaveChatID(senderId, recipientId);
    }

    private String buildAndSaveChatID(String senderId, String recipientId) {

        String chatId =
                String.format("%s_%s", senderId, recipientId);

        MessagingRoom senderRecipient = MessagingRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        MessagingRoom recipientSender = MessagingRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        messagingRoomRepository.save(senderRecipient);
        messagingRoomRepository.save(recipientSender);


        return chatId;
    }
}