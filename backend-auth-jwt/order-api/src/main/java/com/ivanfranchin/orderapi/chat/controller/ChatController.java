package com.ivanfranchin.orderapi.chat.controller;

import com.ivanfranchin.orderapi.chat.entity.ChatMessage;
import com.ivanfranchin.orderapi.chat.model.Message;
import com.ivanfranchin.orderapi.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    // 📬 Public message to all users
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(Message message) throws InterruptedException {
        chatMessageRepository.save(new ChatMessage(
                message.getSenderName(),
                message.getReceiverName(),
                message.getMessage(),
                message.getMedia(),
                message.getMediaType(),
                message.getStatus(),
                System.currentTimeMillis()));
        Thread.sleep(1000); // Optional delay
        return message;
    }

    // 📬 Private 1:1 messaging
    @MessageMapping("/private-message")
    public void privateMessage(Message message) {
        simpMessagingTemplate.convertAndSendToUser(
                message.getReceiverName(), "/private", message);

        chatMessageRepository.save(new ChatMessage(
                message.getSenderName(),
                message.getReceiverName(),
                message.getMessage(),
                message.getMedia(),
                message.getMediaType(),
                message.getStatus(),
                System.currentTimeMillis()));
    }

    // 📜 Chat history between two users
    @GetMapping("/messages/history/{user1}/{user2}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2) {
        List<ChatMessage> messages = chatMessageRepository.findByReceiverNameOrSenderName(user1, user2);
        return ResponseEntity.ok(messages);
    }
}
