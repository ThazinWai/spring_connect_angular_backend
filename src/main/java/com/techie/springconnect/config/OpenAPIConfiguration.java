package com.techie.springconnect.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI expenseAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Connect API-tzw")
                        .version("1.0")
                        .description("API for spring connect angular application!")
                        .license(new License().name("Apache License Version 2.0").url("")));
    }
}
