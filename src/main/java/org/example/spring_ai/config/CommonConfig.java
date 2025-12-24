package org.example.spring_ai.config;

import org.example.spring_ai.memory.DbChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public ChatClient chatClient(OllamaChatModel model, DbChatMemory DbChatMemory) {
        return ChatClient
                .builder(model)
                .defaultSystem("你是个傻傻的，唐唐的智能助手，你的名字叫菜汗，请以菜汗的身份和语气回答问题")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(DbChatMemory).build()
                )
                .build();
    }
}
