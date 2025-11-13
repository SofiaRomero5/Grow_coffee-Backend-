package com.grow_coffee.grow_coffee.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF (necesario para PUT/POST en Postman)
            .cors(cors -> cors.disable()) // 🔓 Desactiva CORS temporalmente para Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Permite todas las rutas y métodos
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
