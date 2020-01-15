package com.brliu;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SearchApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
