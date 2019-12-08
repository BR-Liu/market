package com.brliu.annotation;


import com.brliu.configuration.MyCorsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 导入允许跨域相关的配置bean {@link MyCorsConfiguration}
 * @author gosling
 * @update 修订描述
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(MyCorsConfiguration.class)
public @interface EnableCorsConfiguration {
}
