package org.example.spring_ai.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_ai.entity.MessageVO;
import org.example.spring_ai.memory.DbChatMemory;
import org.example.spring_ai.repository.ChatHistoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;

    private final DbChatMemory chatMemory;

    @RequestMapping("{type}")
    public List<String> getchatIds(@PathVariable String type) {
        List<String> chatIds = chatHistoryRepository.getChatIds(type);
        Collections.reverse(chatIds);
        return chatIds;
    }

    @RequestMapping("{type}/{chatId}")
    public List<MessageVO> getchat(@PathVariable String type, @PathVariable String chatId) {
        List<Message> list = chatMemory.get(chatId);
        List<MessageVO> result = new ArrayList<>();
        for (Message message : list) {
            MessageVO messageVO = new MessageVO(message);
            result.add(messageVO);
        }
        return result;
    }
}
