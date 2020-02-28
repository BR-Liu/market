package com.brliu.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageConsumer {

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "order-point", durable = "true"),
                    exchange = @Exchange(value = "order-1", type = "topic", ignoreDeclarationExceptions = "true"),
                    key = "order.*"
            )
    )
    public void onMessage(Message message, Channel channel) throws Exception {
        Long tag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag, false);
    }


}
