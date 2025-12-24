package org.example.spring_ai.repository;

import org.example.spring_ai.entity.ChatSession;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DbChatHistoryRepository implements ChatHistoryRepository {
    private final ChatSessionRepository chatSessionRepository;

    public DbChatHistoryRepository(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }

    @Override
    public void save(String type, String chatId) {
        if (!chatSessionRepository.existsByChatId(chatId)) {
            ChatSession chatSession = new ChatSession();
            chatSession.setType(type);
            chatSession.setChatId(chatId);
            chatSessionRepository.save(chatSession);
        }
    }

    @Override
    public List<String> getChatIds(String type) {
        return chatSessionRepository.findByType(type)
                .stream()
                .map(ChatSession::getChatId)
                .collect(Collectors.toList());
    }
}