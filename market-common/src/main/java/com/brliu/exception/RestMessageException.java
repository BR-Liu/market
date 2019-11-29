package com.brliu.exception;


import com.brliu.enums.ResponseStateEnum;

/**
 * @author gosling
 * @description 用于controller层抛出异常, 该异常会被拦截器自动封装成ResponseMessage对象
 * @update 修订描述
 */
public class RestMessageException extends RuntimeException {
    private ResponseStateEnum responseStateEnum;

    public RestMessageException(ResponseStateEnum responseStateEnum, String message) {
        super(message);
        this.responseStateEnum = responseStateEnum;
    }

    public ResponseStateEnum getState() {
        return this.responseStateEnum;
    }
}
