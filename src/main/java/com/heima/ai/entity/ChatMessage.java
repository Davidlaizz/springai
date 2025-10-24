package com.heima.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记录聊天具体对白
 */
@Data
@Builder
public class ChatMessage {
    private Long id;
    private String conversationId;
    private String role; // "USER", "ASSISTANT"
    private String content;
}