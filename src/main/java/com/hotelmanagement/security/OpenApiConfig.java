package com.hotelmanagement.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelManagementOpenAPI() {

        return new OpenAPI().info(new Info().title("Hotel Management System API").version("1.0").description("Backend APIs for Hotel Management System").contact(new Contact().name("Harsh Saini")));

    }

}