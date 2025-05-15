package com.couriersync.backendenvios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private String role;
    String message;
    boolean success;
    private String token;
}
