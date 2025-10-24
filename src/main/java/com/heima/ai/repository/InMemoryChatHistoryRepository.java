package com.heima.ai.repository;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryChatHistoryRepository implements ChatHistoryRepository{

    private final Map<String, List<String>> chatHistory = new HashMap<>(); //key: 业务类型，value: 会话ID列表

    /**
     * 保存聊天记录
     *
     * @param type   业务类型，如：chat，service，pdf
     * @param chatId 聊天会话ID
     */
    @Override
    public void save(String type, String chatId) {
        /*
        if(!chatHistory.containsKey(type)){
            chatHistory.put(type, new ArrayList<>());
        }
        List<String> chatIds = chatHistory.get(type);
        以上代码可以简化为下面一行代码          */
        System.out.println("保存聊天记录：" + type + " " + chatId);
        List<String> chatIds = chatHistory.computeIfAbsent(type, k -> new ArrayList<>());

        if (chatIds.contains(chatId)){
            return;
        }
        chatIds.add(chatId);
    }

    /**
     * 删除聊天记录
     *
     * @param type
     * @param chatId
     */
    @Override
    public void delete(String type, String chatId) {
        List<String> chatIds = chatHistory.get(type);
        if (chatIds != null) {
            chatIds.remove(chatId);
        }
    }

    /**
     * 获取聊天记录
     *
     * @param type 业务类型，如：chat，service，pdf
     * @return 会话ID列表
     */
    @Override
    public List<String> getChatIds(String type) {
        /*if (!chatHistory.containsKey(type))
         {
             return new ArrayList<>();
         }
         return chatHistory.get(type);
         简化为以下一行代码
         */
        return chatHistory.getOrDefault(type, List.of());
    }
}
