package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.LoginRequestDTO;
import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;
import com.couriersync.backendenvios.mappers.AuthMapper;
import com.couriersync.backendenvios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    // Usamos BCrypt para el hashing seguro de las contraseñas.
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            // Retorna respuesta de error si no se encuentra el usuario.
            return new LoginResponseDTO(null, null, null, null, "User not found", false);
        }
        User user = userOpt.get();
/*
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Retorna respuesta de error si la contraseña no coincide.
            return new LoginResponseDTO(null, null, null, null, "Invalid credentials", false);
        }
*/
        // Temporalmente usa comparación directa
        if (!request.getPassword().equals(user.getPassword())) {
            return new LoginResponseDTO(null, null, null, null, "Invalid credentials", false);
        }

        // En caso de éxito, usamos el mapper para transformar la entidad en DTO,
        // el mapper debe agregar "Login successful" y success = true.
        return AuthMapper.toLoginResponse(user);
    }
}