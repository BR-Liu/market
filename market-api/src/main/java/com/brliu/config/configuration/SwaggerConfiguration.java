package com.brliu.config.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    //官方文档路径：http://localhost:8088/swagger-ui.html
    //引入的非官方样式文档路径 http://localhost:8088/doc.html
    private final String BASE_PACKAGES = "com.brliu.controller";

    @Bean
    public Docket createSwaggerApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGES))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("BrLiu的电商项目")
                .contact(
                        new Contact(
                                "BrLiu",
                                "http://localhost:8088",
                                "liuxin999999@sohu.com"
                        )
                )
                .version("V0.0.1")
                .description("电商项目")
                .termsOfServiceUrl("http://localhost:8088/")
                .build();
    }
}


