package com.dimevision.redmadrobots.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

/**
 * @author Dimevision
 * @version 0.1
 */

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoom {
    @Id
    private String id;

    @Indexed
    private String chatId;

    @Indexed
    private String senderId;

    @Indexed
    private String recipientId;
}
