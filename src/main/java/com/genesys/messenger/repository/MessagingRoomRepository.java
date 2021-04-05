package com.genesys.messenger.repository;


import com.genesys.messenger.model.MessagingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessagingRoomRepository extends MongoRepository<MessagingRoom, String> {
    MessagingRoom findBySenderIdAndRecipientId(String senderId, String recipientId);
    List<MessagingRoom> findChatRoomBySenderIdAndRecipientIdAndAndChatId(String senderId, String recipientId,
                                                                         String chatId);
}
