package com.couriersync.backendenvios.mappers;

import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;

public class AuthMapper {
    public static LoginResponseDTO toLoginResponse(User user) {
        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                "Login successful",
                true
        );
    }
}
