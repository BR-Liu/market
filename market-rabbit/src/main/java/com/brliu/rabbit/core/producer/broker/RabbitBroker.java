package com.brliu.rabbit.core.producer.broker;

import com.brliu.rabbit.api.Message;

public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);

    void batchSend();
}
