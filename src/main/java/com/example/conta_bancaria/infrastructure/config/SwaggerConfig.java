package com.example.conta_bancaria.infrastructure.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI oficinaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Conta Bancaria")
                        .description("Cadastro e gestão de serviços de um banco.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipe Banco")
                                .email("suporte@banco.com"))
                );
    }
}
