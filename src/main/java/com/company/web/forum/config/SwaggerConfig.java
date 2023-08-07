package com.company.web.forum.config;

import io.swagger.annotations.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.web.forum"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sex-Life Problems Forum")
                .description("API documentation for the Sex-Life Problems Forum application")
                .version("1.0.0")
                //.contact(new Contact("Krasimir Pashov, Nikolay Milanov, Adelina Velinova", "https://gitlab.com/Krasimir.Pashov/moviedb.git", "krasimir.k.pashov@gmail.com, adi_velinova@abv.bg"))
                .build();
    }
}

