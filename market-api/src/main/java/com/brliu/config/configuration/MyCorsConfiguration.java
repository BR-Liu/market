package com.brliu.config.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfiguration {

    @Value("${web.url}")
    private String WEB_URL;

    @Bean
    public CorsFilter corsFilter() {
        //使用spring的跨域配置类
        CorsConfiguration config = new CorsConfiguration();
        //允许跨域的源url
        config.addAllowedOrigin(WEB_URL);
        //允许携带认证参数如cookie
        config.setAllowCredentials(true);
        //允许使用所有头
        config.addAllowedHeader("*");
        //允许使用所有http请求方法
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //所有形式的url路由使用该配置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
