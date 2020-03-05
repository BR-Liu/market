package com.brliu.rabbit.common.serializer;

public interface Serializer {

    byte[] serializeRaw(Object object);

    String serialize(Object object);

    <T> T deserialize(String content);

    <T> T deserialize(byte[] content);
}
