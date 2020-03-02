package com.brliu.rabbit.api;

import com.brliu.rabbit.api.exception.MessageException;

import java.util.List;

public interface MessageProducer {

    void send(Message message) throws MessageException;

    void send(Message message, SendCallBack sendCallBack) throws MessageException;

    void batchSend(List<Message> messagesList) throws MessageException;

}
