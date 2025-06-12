package com.couriersync.backendenvios.controllers;

import com.couriersync.backendenvios.dtos.LoginRequestDTO;
import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authService.authenticate(request);
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping("/refresh-token")
    public LoginResponseDTO refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        String oldAccessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            oldAccessToken = authorizationHeader.substring(7);
        }

        if (oldAccessToken == null || oldAccessToken.isEmpty()) {
            return new LoginResponseDTO(null, null, null, null, "Authorization header missing or invalid.", false, null);
        }

        return authService.refreshToken(oldAccessToken);
    }

}
