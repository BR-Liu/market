package com.brliu.annotation;

import java.lang.annotation.*;


/**
 * @author gosling
 * @description 修饰方法或类，用于自动包装controller返回的对象为ResponseMessage
 * @update 修订描述
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
