package com.abbos.financetrackerbot.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:14
 **/
@Configuration
public class OpenApiConfig {

    @Value("${project.host}")
    private String HOST;
    @Value("${server.port}")
    private String PORT;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Finance Tracker")
                        .description("This API provides endpoints for managing expenses,customers and incomes")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Aliabbos Ashurov")
                                .email("aliabbosashurov@gmail.com")
                                .url("t.me/Aliabbos_Ashurov")
                                .url("https://github.com/Aliabbos-Ashurov"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("https://docs.spring.io/spring-boot/index.html"))
                .externalDocs(new ExternalDocumentation()
                        .description("Finance Tracker Documentation")
                        .url("https://financetracker.wiki.github.org/docs"))
                .servers(List.of(
                        new Server().url("http://" + HOST + ":" + PORT).description("Production Server"),
                        new Server().url("http://localhost:8080").description("Testing Server")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                        .addList("bearer-jwt"));
    }

    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/v1/user/**")
                .build();
    }
}
