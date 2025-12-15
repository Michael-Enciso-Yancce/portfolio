package com.portfolio.michael.shared.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                // 🔹 Información general de tu API
                                .info(new Info()
                                                .title("API Portfolio Michael")
                                                .description("Documentación de la API de Portfolio con Spring Boot 3 + Swagger")
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                                .name("Michael Dev")
                                                                .email("michael@miempresa.com")
                                                                .url("https://mi-portafolio.com"))
                                                .license(new License()
                                                                .name("Apache 2.0")
                                                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                                // 🔹 Servidores disponibles
                                .servers(List.of(
                                                new Server().url("/").description(
                                                                "Servidor por defecto (Detectado automáticamente)")))
                                // 🔹 Configuración de Seguridad (JWT)
                                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                                                .addList("Bearer Authentication"))
                                .components(new io.swagger.v3.oas.models.Components()
                                                .addSecuritySchemes("Bearer Authentication",
                                                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                                                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")));
        }
}
