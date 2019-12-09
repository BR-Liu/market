package com.brliu;

import com.brliu.annotation.EnableCorsConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableCorsConfiguration
@MapperScan(basePackages = "com.brliu.mapper")
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        //SpringApplication.run(Application.class, args);
    }
}
