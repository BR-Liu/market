package com.brliu.rabbit.api;

import com.brliu.rabbit.api.exception.MessageRuntimeException;

import java.util.List;

public interface MessageProducer {

    void send(Message message) throws MessageRuntimeException;

    void send(Message message, SendCallBack sendCallBack) throws MessageRuntimeException;

    void batchSend(List<Message> messagesList) throws MessageRuntimeException;

}
