package com.macronnect.sales_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI salesApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Sales API")
                        .description("REST API for managing clients, products, sales and sale details.")
                        .version("1.0.0"));
    }
}
