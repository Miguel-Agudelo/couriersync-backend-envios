package com.couriersync.backendenvios.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@Relation
public class LoginResponseDTO extends RepresentationModel<LoginResponseDTO> {
    private Integer id;
    private String name;
    private String email;
    private String role;
    String message;
    boolean success;
    private String token;
}
