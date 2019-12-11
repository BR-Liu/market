package com.brliu.annotation;


import com.brliu.configuration.CorsCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Conditional(CorsCondition.class)  //条件注解的真正实现，引入实现了Condition接口的类
public @interface ConditionSwitch {
    boolean isOpen() default false;
}
