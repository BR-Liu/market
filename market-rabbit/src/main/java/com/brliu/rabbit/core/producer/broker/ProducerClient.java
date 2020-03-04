package com.brliu.rabbit.core.producer.broker;

import com.brliu.rabbit.api.Message;
import com.brliu.rabbit.api.MessageProducer;
import com.brliu.rabbit.api.MessageType;
import com.brliu.rabbit.api.SendCallBack;
import com.brliu.rabbit.api.exception.MessageRuntimeException;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRuntimeException {
        Preconditions.checkNotNull(message);
        switch (message.getMessageType()) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(Message message, SendCallBack sendCallBack) throws MessageRuntimeException {

    }

    @Override
    public void batchSend(List<Message> messagesList) throws MessageRuntimeException {

    }
}
