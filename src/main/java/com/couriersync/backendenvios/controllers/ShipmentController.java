package com.couriersync.backendenvios.controllers;

import com.couriersync.backendenvios.dtos.ShipmentCreationRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentUpdateRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentSummaryResponseDTO;
import com.couriersync.backendenvios.services.ShipmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR')")
    @PostMapping("/create")
    public ResponseEntity<Void> createShipment(@Valid @RequestBody ShipmentCreationRequestDTO request,
                                               Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getName());
        System.out.println("User id: " + userId);
        shipmentService.createShipment(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateShipment(@PathVariable("id") Integer shipmentId, @RequestBody @Valid ShipmentUpdateRequestDTO dto) {
        try {
            shipmentService.updateShipment(shipmentId, dto);
            return ResponseEntity.ok("Shipment updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')")
    @GetMapping("/listOne/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable Integer id) {
        try {
            ShipmentResponseDTO dto = shipmentService.getShipmentById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')")
    @GetMapping("/list")
    public ResponseEntity<List<ShipmentResponseDTO>> getAllShipments() {
        List<ShipmentResponseDTO> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')")
    @PutMapping("/status/transit/{id}")
    public ResponseEntity<String> updateStatusToInTransit(@PathVariable("id") Integer shipmentId) {
        try {
            shipmentService.updateShipmentStatusToInTransit(shipmentId);
            return ResponseEntity.ok("Shipment status updated to 'En transito'");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRADOR', 'ROLE_OPERADOR', 'ROLE_CONDUCTOR')")
    @PutMapping("/status/delivered/{id}")
    public ResponseEntity<String> updateShipmentToDelivered(@PathVariable("id") Integer shipmentId) {
        try {
            shipmentService.updateShipmentStatusToDelivered(shipmentId);
            return ResponseEntity.ok("Shipment status updated to 'Entregado''");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/summary")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ShipmentSummaryResponseDTO> getShipmentSummary() {
        ShipmentSummaryResponseDTO summary = shipmentService.getShipmentSummaryForAdmin();
        return ResponseEntity.ok(summary);
    }
}