package com.sparta.topster.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                String corsOrigins = System.getenv("CORS_ORIGINS");
                String[] allowedOrigins = new String[] {};
                if (corsOrigins != null) {
                    allowedOrigins = corsOrigins.split(",");
                }
                registry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .exposedHeaders("Authorization")
                    .allowedOrigins(allowedOrigins);
            }
        };
    }

}
