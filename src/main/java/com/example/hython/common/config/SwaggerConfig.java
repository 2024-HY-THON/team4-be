package com.example.hython.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI SpotAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HY-THON TEAM 4 API")
                        .description("This is the API documentation for the HY-THON TEAM 4 API")) // OpenAPI 버전 정보 추가
                .addServersItem(new Server().url("/"));
    }
}
