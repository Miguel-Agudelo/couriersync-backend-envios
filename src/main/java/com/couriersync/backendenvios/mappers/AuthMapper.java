package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;

public class AuthMapper {

    public static LoginResponseDTO fromUserToLoginResponse(
            User user,
            String token) {
        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                "Login successful",
                true,
                token
        );
    }

}
