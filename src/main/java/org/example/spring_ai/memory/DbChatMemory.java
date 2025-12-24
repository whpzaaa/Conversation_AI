package org.example.spring_ai.memory;

import org.example.spring_ai.entity.ChatMessage;
import org.example.spring_ai.repository.ChatMessageRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbChatMemory implements ChatMemory {
    private final ChatMessageRepository chatMessageRepository;

    public DbChatMemory(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        for (Message message : messages) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setChatId(conversationId);
            chatMessage.setContent(message.getText());

            switch (message.getMessageType()) {
                case USER:
                    chatMessage.setRole("user");
                    break;
                case ASSISTANT:
                    chatMessage.setRole("assistant");
                    break;
                default:
                    chatMessage.setRole(message.getMessageType().name().toLowerCase());
            }

            chatMessageRepository.save(chatMessage);
        }
    }


    @Override
    public List<Message> get(String conversationId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatIdOrderByTimestampAsc(conversationId);
        List<Message> messages = new ArrayList<>();
        
        for (ChatMessage chatMessage : chatMessages) {
            String role = chatMessage.getRole();
            String content = chatMessage.getContent();

            // 按角色转换为对应的 Message 实现类
            MessageType messageType;
            try {
                messageType = MessageType.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                // 处理未知角色，可抛出异常或默认转为系统消息
                messageType = MessageType.SYSTEM;
            }

            switch (messageType) {
                case USER:
                    messages.add(new UserMessage(content));
                    break;
                case ASSISTANT:
                    messages.add(new AssistantMessage(content));
                    break;
                default:
                    // 使用 SystemMessage 处理其他类型，避免直接实现 Message 接口
                    messages.add(new org.springframework.ai.chat.messages.SystemMessage(content));
                    break;
            }
        }
        
        return messages;
    }

    @Override
    public void clear(String chatId) {
        chatMessageRepository.deleteByChatId(chatId);
    }
}