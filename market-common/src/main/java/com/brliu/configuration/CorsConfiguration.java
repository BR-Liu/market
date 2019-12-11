package com.brliu.configuration;


import com.brliu.annotation.ConditionSwitch;
import com.brliu.annotation.EnableCorsConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration   //此处只要是模式注解都可以，保证被spring扫描
@EnableCorsConfiguration  //自定义能力注解，三要素之一
@ConditionSwitch(isOpen = true) //自定义条件注解，三要素之一
public class CorsConfiguration {
}
