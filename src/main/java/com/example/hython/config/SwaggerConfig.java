package com.example.hython.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI SpotAPI() {
        // Define SecurityScheme
        Info info = new Info()
                .title("HY-THON TEAM 4 API")
                .description("This is the API documentation for the HY-THON TEAM 4 API");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
        }
    }