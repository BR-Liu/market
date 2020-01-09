package com.brliu;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.brliu.mapper")
public class SSOApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SSOApplication.class)
                .profiles("dev")
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
