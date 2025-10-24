package com.heima.ai.controller;

import com.heima.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    @Autowired
    @Qualifier("inSqlChatHistoryRepository") //会话历史列表：sql模式存储
    private ChatHistoryRepository chatHistoryRepository;

//    @RequestMapping("/chat")
//    public String chat(String prompt) {
//        return  chatClient.prompt()
//                .user(prompt)
//                .call()
//                .content();
//    }
    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(String prompt, String chatId) {
        // 1.保存会话
        chatHistoryRepository.save("chat", chatId);
        // 2.请求模型
        return  chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
