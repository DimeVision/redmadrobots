package com.dimevision.redmadrobots.controller.deal;

import com.dimevision.redmadrobots.config.websocket.ChatMessageService;
import com.dimevision.redmadrobots.config.websocket.ChatRoomService;
import com.dimevision.redmadrobots.config.websocket.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Controller
@RequiredArgsConstructor
public class DealChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getReceiverId());
        chatMessage.setChatId(chatId.orElseThrow());

        messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/queue/messages", chatMessage);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
