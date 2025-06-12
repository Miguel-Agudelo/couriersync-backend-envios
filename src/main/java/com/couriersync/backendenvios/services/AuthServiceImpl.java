package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.LoginRequestDTO;
import com.couriersync.backendenvios.dtos.LoginResponseDTO;
import com.couriersync.backendenvios.entities.User;
import com.couriersync.backendenvios.mappers.AuthMapper;
import com.couriersync.backendenvios.repositories.UserRepository;
import com.couriersync.backendenvios.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureException;
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
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, null, null, "Invalid credentials", false, null);
        }
        String token = jwtUtil.createToken(user);
        return AuthMapper.fromUserToLoginResponse(user, token);
    }

    @Override
    public LoginResponseDTO refreshToken(String oldAccessToken) {
        Claims claims;
        try {
            claims = jwtUtil.getClaims(oldAccessToken);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (SignatureException e) {
            return new LoginResponseDTO(null, null, null, null, "Invalid JWT signature or token malformed.", false, null);
        } catch (Exception e) {
            return new LoginResponseDTO(null, null, null, null, "Invalid token provided for refresh: " + e.getMessage(), false, null);
        }
        try {
            Integer userId = claims.get("id_user", Integer.class);
            if (userId == null) {
                return new LoginResponseDTO(null, null, null, null, "User ID not found in token claims.", false, null);
            }
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for ID in token."));
            String newAccessToken = jwtUtil.createToken(user);
            return AuthMapper.fromUserToLoginResponse(user, newAccessToken);
        } catch (RuntimeException e) {
            return new LoginResponseDTO(null, null, null, null, "Failed to refresh token: " + e.getMessage(), false, null);
        }
    }

}
