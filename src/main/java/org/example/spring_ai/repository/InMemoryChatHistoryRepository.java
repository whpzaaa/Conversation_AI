//package org.example.spring_ai.repository;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//public class InMemoryChatHistoryRepository implements ChatHistoryRepository {
//    private final HashMap<String,List<String>> map = new HashMap<>();
//    @Override
//    public void save(String type, String chatId) {
//        if(!map.containsKey(type)){
//            map.put(type,new ArrayList<>());
//        }
//        List<String> chatIds = map.get(type);
//        if(!chatIds.contains(chatId)){
//            chatIds.add(chatId);
//        }
//    }
//
//    @Override
//    public List<String> getChatIds(String type) {
//        return map.getOrDefault(type,new ArrayList<>());
//    }
//}
