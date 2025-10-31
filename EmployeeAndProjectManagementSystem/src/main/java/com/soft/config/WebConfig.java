

package com.soft.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // <-- CORRECT INTERFACE

/**
 * Corrected Web MVC Configuration for Spring Boot 3+
 * The deprecated 'WebMvcConfigurerAdapter' has been replaced by the 'WebMvcConfigurer' interface.
 */
@Configuration
// Change 'extends WebMvcConfigurerAdapter' to 'implements WebMvcConfigurer'
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures global CORS (Cross-Origin Resource Sharing) rules.
     * This is crucial for connecting a frontend (e.g., React on port 3000) to the backend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/") // Apply CORS only to your API endpoints
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000") // Replace with your actual frontend URL(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}