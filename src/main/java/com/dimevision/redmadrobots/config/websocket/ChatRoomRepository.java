package com.dimevision.redmadrobots.config.websocket;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.Optional;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface ChatRoomRepository extends KeyValueRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
