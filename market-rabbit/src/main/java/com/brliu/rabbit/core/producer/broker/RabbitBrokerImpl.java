package com.brliu.rabbit.core.producer.broker;

import com.brliu.rabbit.api.Message;
import com.brliu.rabbit.api.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息的核心发送类
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RabbitBrokerImpl implements RabbitBroker {

    private final RabbitTemplateContainer rabbitTemplateContainer;

    /**
     * 快速消息
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * 确认消息
     */
    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    /**
     * 可靠消息
     */
    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        sendKernel(message);
    }

    /**
     * 批量消息
     */
    @Override
    public void batchSend() {

    }

    /**
     * 各种消息类型的核心发送方法，采用异步方式提高效率
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit(() -> {
            String msgId = message.getMessageId();
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s", msgId, System.currentTimeMillis()));
            String routingKey = message.getRoutingKey();
            RabbitTemplate template = rabbitTemplateContainer.getTemplate(message);
            template.convertAndSend(routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to mq , the message id is : {}", msgId);
        });
    }
}
