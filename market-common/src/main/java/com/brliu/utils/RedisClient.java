package com.brliu.utils;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisClient {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取字符串对象
     */
    public String get(String id) {
        String value = (String) redissonClient.getBucket(id).get();
        return value;
    }

    /**
     * 设置字符串
     */
    public void set(String id, String value) {
        redissonClient.getBucket(id).set(value);
    }

}
