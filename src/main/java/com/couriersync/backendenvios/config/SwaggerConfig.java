package com.couriersync.backendenvios.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "CourierSync API", version = "1.0", description = "Documentación de la API de envíos"),
        security = @SecurityRequirement(name = "Authorization") // <-- Este es el nombre interno
)
@SecurityScheme(
        name = "Authorization",               // <-- Nombre interno usado en @SecurityRequirement
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER         // <-- Ubicación real del token
)
public class SwaggerConfig {
}
