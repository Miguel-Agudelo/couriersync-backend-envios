package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.controllers.AuthController; // Importar el controlador
import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class AuthMapper {

    public static LoginResponseDTO fromUserToLoginResponse(User user, String token) {
        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                "Login successful",
                true,
                token
        );
        response.add(linkTo(methodOn(AuthController.class).refreshToken(null)).withRel("refresh-token"));
        return response;
    }
}