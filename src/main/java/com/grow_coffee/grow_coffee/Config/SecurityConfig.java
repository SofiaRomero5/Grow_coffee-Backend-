package com.grow_coffee.grow_coffee.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para pruebas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // Permite libre acceso a todo /api
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
