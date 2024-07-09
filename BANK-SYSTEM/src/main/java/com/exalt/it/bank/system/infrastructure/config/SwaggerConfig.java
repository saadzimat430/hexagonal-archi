package com.exalt.it.bank.system.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI applicationOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank System For Exalt IT")
                        .description(
                                "Application that manipulates a simple bank system " +
                                        "with the ability to create, deposit, and withdraw money from accounts. "
                        ).version("0.0.1-SNAPSHOT")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Source code is available at GitHub repository")
                        .url("https://gitlab.com/exalt-it-dojo/candidats/abdelkarim-imghi-bank-account-v2-493239b3-bc3c-4774-9a56-1de1e717f9b8")
                );
    }
}

