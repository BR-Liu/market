package com.brliu;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.brliu.mapper")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        //SpringApplication.run(Application.class, args);
        //context.getBean("corsFilter", CorsFilter.class);
        //context.close();
    }
}
