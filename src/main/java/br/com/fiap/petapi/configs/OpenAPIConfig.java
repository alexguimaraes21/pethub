package br.com.fiap.petapi.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Documento OpenAPI do sistema Pet")
                .description("Sistema para bem estar dos Pets")
                .termsOfService("TERMS OF SERVICE URL")
                .version("v0.0.1")
                .license(new License()
                        .name("Apache 2.0").url("http://springdoc.org/v2"))
                .contact(new Contact()
                        .name("Grupo")
                        .url("")
                        .email("")))
                .externalDocs(new ExternalDocumentation()
                        .description("Sistema para bem estar dos Pets"))
                .components(new Components()
                        .addSecuritySchemes("token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
