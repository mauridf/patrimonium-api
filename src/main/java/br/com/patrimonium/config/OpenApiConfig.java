package br.com.patrimonium.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI patrimoniumOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Patrimonium API")
                        .description("Sistema de Gestão Imobiliária Pessoal")
                        .version("v1.0.0"));
    }
}