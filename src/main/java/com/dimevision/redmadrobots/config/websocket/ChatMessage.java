package com.dimevision.redmadrobots.config.websocket;

import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Dimevision
 * @version 0.1
 */

@Data
public class ChatMessage {
    @Id
    private String id;

    @Indexed
    private String chatId;

    @Indexed
    private String senderId;

    @Indexed
    private String senderName;

    @Indexed
    private String receiverId;

    @Indexed
    private String receiverName;

    private String message;

    private LocalDateTime dateTime;
}
