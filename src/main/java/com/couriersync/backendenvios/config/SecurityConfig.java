package com.couriersync.backendenvios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Declara esta clase como una clase de configuración de Spring Security
@Configuration
public class SecurityConfig {

    // Define un bean que configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva la protección CSRF (útil para APIs REST sin sesiones)
                .csrf().disable()

                // Configura las reglas de autorización para las solicitudes HTTP
                .authorizeHttpRequests()

                // Permite el acceso público al endpoint de login
                .requestMatchers("/api/auth/login").permitAll()

                // Requiere autenticación para cualquier otra solicitud
                .anyRequest().authenticated();

        // Construye y retorna el objeto SecurityFilterChain
        return http.build();
    }

    // Define un bean que proporciona el codificador de contraseñas BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

