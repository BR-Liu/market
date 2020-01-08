package com.brliu.utils;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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


    /**
     * 设置字符串，带过期时间参数
     */
    public void set(String id, String value, long expireTime, TimeUnit unit) {
        redissonClient.getBucket(id).set(value, expireTime, unit);
    }

    /**
     * 删除键
     */
    public void delete(String id) {
        redissonClient.getBucket(id).delete();
    }


}
