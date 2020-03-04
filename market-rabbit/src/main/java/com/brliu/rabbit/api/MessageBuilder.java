package com.brliu.rabbit.api;

import com.brliu.rabbit.api.exception.MessageRuntimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MessageBuilder {

    private String messageId;

    private String topic;

    private String routingKey = "";

    private Map<String, Object> attributes = new HashMap<>();

    private long delayMills;

    private String messageType = MessageType.CONFIRM;

    private MessageBuilder() {
    }

    public MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withAttribute(String name, Object value) {
        this.attributes.put(name, value);
        return this;
    }

    public MessageBuilder withDelayMills(long delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() {
        if (Objects.isNull(messageId)) {
            this.messageId = UUID.randomUUID().toString();
        }
        if (Objects.isNull(topic)) {
            throw new MessageRuntimeException("未设置消息主题");
        }
        return new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
    }
}
