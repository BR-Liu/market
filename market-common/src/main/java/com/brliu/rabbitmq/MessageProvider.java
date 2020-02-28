package com.brliu.rabbitmq;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageProvider {

    private RabbitTemplate rabbitTemplate;

    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, b, s) -> {
        if (b) {
            System.out.println("ok");
        }
    };

    public void sender(Object message, Map<String, Object> headers) {
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);

        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("exchange_1",
                "order.*",
                msg,
                message1 -> {
                    System.out.println("message : " + message1);
                    return message1;
                },
                correlationData);
    }


}
