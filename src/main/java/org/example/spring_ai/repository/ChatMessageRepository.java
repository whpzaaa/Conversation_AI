package org.example.spring_ai.repository;

import org.example.spring_ai.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);
    void deleteByChatId(String chatId);
}