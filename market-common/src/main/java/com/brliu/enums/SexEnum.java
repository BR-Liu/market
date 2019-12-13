package com.brliu.enums;

/**
 * @Desc: 性别 枚举
 */
public enum SexEnum {
    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");

    public final Integer type;
    public final String value;

    SexEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
