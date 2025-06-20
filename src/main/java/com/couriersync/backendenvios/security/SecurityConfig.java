package com.couriersync.backendenvios.security;

import com.couriersync.backendenvios.filters.JwtTokenFilter;
import com.couriersync.backendenvios.services.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
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
                .cors(withDefaults())
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh-token").authenticated() // El refresh token debe estar autenticado
                        .requestMatchers("/api/shipments/summary").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers("/api/shipments/**").hasAnyAuthority("ROLE_OPERADOR", "ROLE_CONDUCTOR", "ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/addresses/create").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/api/addresses/list").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_OPERADOR", "ROLE_CONDUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/clients/create").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/api/clients/list").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_OPERADOR", "ROLE_CONDUCTOR")
                        .requestMatchers("/actuator/prometheus").permitAll()

                        // Todo lo demás requiere solo autenticación
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
