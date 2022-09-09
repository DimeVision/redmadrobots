package com.dimevision.redmadrobots.config.websocket;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface ChatMessageRepository extends KeyValueRepository<ChatMessage, String> {

    List<ChatMessage> findAllByChatId(String cId);
}
