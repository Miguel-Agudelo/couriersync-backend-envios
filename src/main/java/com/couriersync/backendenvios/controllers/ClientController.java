package com.couriersync.backendenvios.controllers;

import com.couriersync.backendenvios.dtos.ClientRequestDTO;
import com.couriersync.backendenvios.dtos.ClientResponseDTO;
import com.couriersync.backendenvios.services.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR')")
    @PostMapping("/create")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequestDTO requestDTO) {
        try {
            ClientResponseDTO response = clientService.createClient(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')")
    @GetMapping("/list")
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')") // O los roles que apliquen
    @GetMapping("/{id}") // Nueva ruta
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable Integer id) {
        ClientResponseDTO client = clientService.getById(id); // Necesitarás un método getById en ClientService
        return ResponseEntity.ok(client);
    }
}