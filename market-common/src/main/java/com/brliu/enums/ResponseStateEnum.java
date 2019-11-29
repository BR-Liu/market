package com.brliu.enums;

import java.util.Arrays;

/**
 * @Description  java类作用描述
 * @Author       gosling
 * @CreateDate   2019/11/30 12:10 上午
 * @Version      1.0
 */public enum ResponseStateEnum {

    SUCCESS(200),
    PARAM_ERROR(400),
    TOKEN_INVALID(401),
    EXISTED(402),
    AUTH_ERROR(403),
    NOT_EXIST(404),
    SERVER_ERROR(500),
    EXPIRED(501);

    private final int state;

    private ResponseStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public static ResponseStateEnum getState(int state) {
        return Arrays.stream(ResponseStateEnum.values()).filter(ResponseStateEnum -> ResponseStateEnum.getState() == state)
                .findFirst().get();
    }
}
