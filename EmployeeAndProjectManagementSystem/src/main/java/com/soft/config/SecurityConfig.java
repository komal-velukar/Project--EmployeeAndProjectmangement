

package com.soft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // For @PreAuthorize
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // <-- Import for PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Import for PasswordEncoder
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soft.security.filter.JwtAuthFilter; // Assuming this is the correct package path

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables method-level security like @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter; 

    // 1. CRITICAL FIX: Defines the PasswordEncoder bean required by EmployeeService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 2. Defines the AuthenticationManager Bean (Necessary for Spring Security 6+)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 3. Defines the Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            // Disable CSRF for API development (or configure it properly for forms)
            .csrf(csrf -> csrf.disable()) 
            
            // Configure session management (STATELESS is essential for JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Define URL Authorization Rules
            .authorizeHttpRequests(auth -> auth
                
                // A. Allow ALL Static Resources (CSS, JS, Images)
                .requestMatchers("/css/", "/js/", "/images/").permitAll() 
                .requestMatchers("/api-service.js", "/app.js").permitAll()
                
                // B. Allow All Frontend Paths (Controller Mappings and direct HTML files)
                // This FIXES the TOO_MANY_REDIRECTS and 401 errors for the login/root pages
                .requestMatchers("/", "/login", "/employees", "/proposals", "/leave-requests").permitAll()
                .requestMatchers("/index.html", "/dashboard.html", "/login.html", "/employees.html", "/proposals.html", "/leave-requests.html").permitAll()
                
                // C. Allow public/auth APIs (Crucial for the login process)
                .requestMatchers("/api/v1/auth/").permitAll() 

                // D. Secure all other endpoints
                .anyRequest().authenticated()
            );

        // Add your JWT Filter before the standard authentication process
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}