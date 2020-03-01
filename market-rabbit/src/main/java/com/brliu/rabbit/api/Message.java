package com.brliu.rabbit.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -5042189321970073235L;

    /**
     * 消息唯一id
     */
    private String messageId;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息对应的routingKey
     */
    private String routingKey = "";
    /**
     * 消息头属性
     */
    private Map<String, Object> attributes = new HashMap<>();
    /**
     * 消息延迟时间（如果消息类型时延迟消息）
     */
    private long delayMills;
    /**
     * 消息类型
     */
    private String messageType = MessageType.CONFIRM;

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, long delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }
}
