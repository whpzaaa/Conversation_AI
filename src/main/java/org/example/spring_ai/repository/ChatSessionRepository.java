package org.example.spring_ai.repository;

import org.example.spring_ai.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    List<ChatSession> findByType(String type);
    ChatSession findByChatId(String chatId);
    boolean existsByChatId(String chatId);
}