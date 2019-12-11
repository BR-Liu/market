package com.brliu.annotation;


import com.brliu.configuration.CorsSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 导入允许跨域相关的配置 {@link CorsSelector}
 * 能力注解有两种实现方式，一种是直接import想要导入的bean所在的configuration。{@since 3}
 * 另一种是import实现了ImportSelector接口的类，在其中实现选择导入的configuration {@since 3.1}
 * @author gosling
 */
@Configuration
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(CorsSelector.class)
public @interface EnableCorsConfiguration {

    String name() default "myCors";
}
