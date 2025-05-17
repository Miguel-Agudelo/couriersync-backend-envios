package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.LoginRequestDTO;
import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;
import com.couriersync.backendenvios.mappers.AuthMapper;
import com.couriersync.backendenvios.repositories.UserRepository;
import com.couriersync.backendenvios.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO request) {

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            return new LoginResponseDTO( null,null, null, null, "empty email or password", false,null);
        }
        if (userOpt.isEmpty()) {
            return new LoginResponseDTO( null,null, null, null, "User not found", false,null);
        }

        User user = userOpt.get();

        // Compara la contraseña ingresada con la contraseña encriptada almacenada
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, null, null, "Invalid credentials", false, null);
        }
/*
        // Compara la contraseña proporcionada con la almacenada (sin encriptación en este código)
        if (!request.getPassword().equals(user.getPassword())) {
            return new LoginResponseDTO( null,null, null, null, "Invalid credentials", false,null);
        }
*/

        String token = jwtUtil.createToken(user);

        return AuthMapper.fromUserToLoginResponse(user, token);

    }
}
