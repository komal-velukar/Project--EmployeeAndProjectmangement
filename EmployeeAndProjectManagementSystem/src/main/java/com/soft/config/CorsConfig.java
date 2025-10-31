

package com.soft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// IMPORTANT: This configuration is for Spring Boot 1.x (Spring 4)
// If you use a newer version, use a CorsFilter Bean instead.

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/") // Apply to all API endpoints
                .allowedOrigins("*") // Allows access from any domain (Use specific domain in production)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}