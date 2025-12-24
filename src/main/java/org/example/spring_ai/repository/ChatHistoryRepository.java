package org.example.spring_ai.repository;

import org.springframework.ai.chat.memory.ChatMemory;

import java.util.List;

public interface ChatHistoryRepository {
    public void save(String type,String chatId);

    public List<String> getChatIds(String type);
}
