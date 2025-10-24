package com.heima.ai.controller;

import com.heima.ai.entity.MessageVO;
import com.heima.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
public class ChatHistoryController {

    @Autowired
    @Qualifier("inSqlChatHistoryRepository") //会话历史列表：sql模式存储
    private ChatHistoryRepository chatHistoryRepository;

    private final ChatMemory chatMemory;

    /**
     * 获取聊天记录列表
     * @param type 业务类型，如：chat，service，pdf
     * @return 会话ID列表
     */
    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable String type) {
        return chatHistoryRepository.getChatIds(type);
    }

    /**
     * 根据会话id获取详细聊天记录
     * @param type 业务类型，如：chat，service，pdf
     * @param chatId 会话ID
     * @return 聊天记录
     */
    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable String type, @PathVariable String chatId){
        List<Message> messages = chatMemory.get(chatId);
        if (messages == null) {
            return List.of();
        }
        // 将Message对象转换为MessageVO对象。MessageVO::new表示使用构造方法创建MessageVO对象
        return messages.stream().map(MessageVO::new).toList();
    }

    /**
     * 根据会话id删除聊天会话窗口和聊天记录
     * @param type 业务类型，如：chat，service，pdf
     * @param chatId 会话ID
     * @return 聊天记录
     */
    @DeleteMapping("/{type}/{chatId}")
    public void deleteChatHistory(@PathVariable String type, @PathVariable String chatId){
        // 删除会话列表
        chatHistoryRepository.delete(type, chatId);
        // 删除具体会话
        chatMemory.clear(chatId);
    }
}
