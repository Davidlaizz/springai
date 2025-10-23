package com.heima.ai.controller;

import com.heima.ai.entity.MessageVO;
import com.heima.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;

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


}
