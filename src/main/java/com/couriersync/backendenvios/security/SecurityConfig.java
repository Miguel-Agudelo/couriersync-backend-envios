package com.couriersync.backendenvios.security;

import com.couriersync.backendenvios.filters.JwtTokenFilter;
import com.couriersync.backendenvios.services.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Habilita CORS usando el bean de arriba
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Swagger docs públicos
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"
                        ).permitAll()
                        // Endpoint de login sin token
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        // Endpoint protegidos por rol
                        .requestMatchers("/api/shipments/**").hasAnyAuthority("ROLE_OPERADOR", "ROLE_CONDUCTOR")
                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
