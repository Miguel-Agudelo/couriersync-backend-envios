package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.LoginRequestDTO;
import com.couriersync.backendenvios.dtos.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO authenticate(LoginRequestDTO request);
}
