package com.brliu.rabbit.core.producer.broker;

import com.brliu.rabbit.api.Message;
import com.brliu.rabbit.api.MessageType;
import com.brliu.rabbit.common.convert.GenericMessageConverter;
import com.brliu.rabbit.common.convert.RabbitMessageConverter;
import com.brliu.rabbit.common.serializer.Serializer;
import com.brliu.rabbit.common.serializer.SerializerFactory;
import com.brliu.rabbit.common.serializer.impl.JacksonSerializerFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * RabbitTemplate的池化封装，为每一个topic提供一个RabbitTemplate
 * 根据不同的发送方式制定不同的RabbitTemplate
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private final ConnectionFactory connectionFactory;

    private Splitter idSplitter = Splitter.on("#");

    private SerializerFactory serializerFactory = JacksonSerializerFactory.INSTANCE;

    public RabbitTemplate getTemplate(Message message) {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if (Objects.nonNull(rabbitTemplate)) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# current topic : {} does not have a template ,creating now", topic);
        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        GenericMessageConverter genericMessageConverter = new GenericMessageConverter(serializerFactory.create());
        RabbitMessageConverter rabbitMessageConverter = new RabbitMessageConverter(genericMessageConverter);
        newTemplate.setMessageConverter(rabbitMessageConverter);  //序列化
        String msgType = message.getMessageType();
        if (MessageType.RAPID.equals(msgType)) {
            assert false;
            rabbitTemplate.setConfirmCallback(this);
        }
        rabbitMap.putIfAbsent(topic, rabbitTemplate);
        return newTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        List<String> strings = idSplitter.splitToList(Objects.requireNonNull(correlationData.getId()));
        String id = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        if (ack) {
            log.info("#RabbitTemplateContainer.confirm# send message successfully , confirm messageId : {}, time : {}", id, new Date(sendTime));
        } else {
            log.error("#RabbitTemplateContainer.confirm# send message failed , confirm messageId : {}, time : {}", id, new Date(sendTime));
        }
    }
}
