package com.dimevision.redmadrobots.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        messageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        String chatId = chatRoomService.getChatId(senderId, recipientId).orElseThrow();

        return messageRepository.findAllByChatId(chatId);
    }

    public ChatMessage findById(String id) {
        return messageRepository
                .findById(id)
                .map(messageRepository::save)
                .orElseThrow(() -> new NotFoundException("can't find message (" + id + ")"));
    }
}
