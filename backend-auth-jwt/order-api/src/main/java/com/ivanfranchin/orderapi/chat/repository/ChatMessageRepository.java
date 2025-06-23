package com.ivanfranchin.orderapi.chat.repository;

import com.ivanfranchin.orderapi.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByReceiverNameOrSenderName(String receiverName, String senderName);
}
