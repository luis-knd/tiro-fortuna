package com.tirofortuna.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${tiro-fortuna.openapi.dev-url}")
    private String devUrl;

    @Value("${tiro-fortuna.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(createInfo())
                .servers(createServers());
    }

    private Info createInfo() {
        Contact contact = new Contact()
                .email("luiscandelario41@gmail.com")
                .name("Luis Candelario")
                .url("https://www.lcandesign.com");

        return new Info()
                .title("Tiro Fortuna API")
                .version("1.0")
                .contact(contact)
                .description("""
                    Tiro Fortuna Application is a comprehensive application designed to provide detailed insights into lottery results.
                    This application is tailored for lottery enthusiasts, analysts, and researchers who are interested in understanding
                    the patterns, trends, and outcomes of lottery draws. It leverages historical data to generate statistics that can help
                    users make informed decisions or simply satisfy their curiosity about the unpredictable nature of lottery games.
                """);
    }

    private List<Server> createServers() {
        Server devServer = new Server()
                .url(devUrl)
                .description("Server URL in Development environment");

        Server prodServer = new Server()
                .url(prodUrl)
                .description("Server URL in Production environment");

        return Arrays.asList(devServer, prodServer);
    }
}
