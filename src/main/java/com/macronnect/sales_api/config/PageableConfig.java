package com.macronnect.sales_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfig {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizePageable() {

        return resolver -> {

            // Si el usuario no envía page ni size
            resolver.setFallbackPageable(PageRequest.of(0, 10));

            // Máximo permitido
            resolver.setMaxPageSize(20);

            // Que page empiece en 0
            resolver.setOneIndexedParameters(false);

        };
    }
}
