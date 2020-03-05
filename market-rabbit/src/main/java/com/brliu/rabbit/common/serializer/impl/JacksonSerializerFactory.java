package com.brliu.rabbit.common.serializer.impl;

import com.brliu.rabbit.api.Message;
import com.brliu.rabbit.common.serializer.Serializer;
import com.brliu.rabbit.common.serializer.SerializerFactory;

public class JacksonSerializerFactory implements SerializerFactory {

    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    private JacksonSerializerFactory() {

    }

    @Override

    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
