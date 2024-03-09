package br.com.fiap.petapi.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${cgtecnologia.openaip.dev-url}")
    private String devUrl;

    @Value("${cgtecnologia.openaip.hml-url}")
    private String hmlUrl;

    @Value("${cgtecnologia.openaip.prd-url}")
    private String prdUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in development environment");

        Server hmlServer = new Server();
        hmlServer.setUrl(hmlUrl);
        hmlServer.setDescription("Server URL in Test environment");

        Server prdServer = new Server();
        prdServer.setUrl(prdUrl);
        prdServer.setDescription("Server URL in Production environment");

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
                                .bearerFormat("JWT"))).servers(List.of(devServer, hmlServer, prdServer));
    }

}
