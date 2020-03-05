package com.brliu.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMessageConverter implements MessageConverter {

    private MessageConverter messageConverter;

    private final static String defaultExpire = String.valueOf(24 * 60 * 60 * 1000);

    public RabbitMessageConverter(MessageConverter messageConverter) {
        Preconditions.checkNotNull(messageConverter);
        this.messageConverter = messageConverter;
    }

    @Override

    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        messageProperties.setExpiration(defaultExpire);
        return messageConverter.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return messageConverter.fromMessage(message);
    }

}
