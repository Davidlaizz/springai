package com.heima.ai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * 记录chatId会话历史列表的实体类
  */
@Data
@Builder
public class ChatHistory {
    private String id;
    private String type;
    private String chatId;
}