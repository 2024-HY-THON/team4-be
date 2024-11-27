package com.example.hython.common.config;

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
        // SecurityScheme 이름 지정
        String securitySchemeName = "accessToken";

        return new OpenAPI()
                .info(new Info()
                        .title("HY-THON TEAM 4 API")
                        .description("This is the API documentation for the HY-THON TEAM 4 API"))
                .addServersItem(new Server().url("/"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)) // SecurityRequirement 추가
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName, // SecurityScheme 추가
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))); // Bearer 토큰 형식으로 설정
    }
}
