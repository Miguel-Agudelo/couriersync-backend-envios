package com.couriersync.backendenvios.controllers;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping("/create")
    public ResponseEntity<ShipmentRequestDTO> createShipment(@Valid @RequestBody ShipmentRequestDTO request,
                                                              Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getName());
        System.out.println("User id: " + userId);
        shipmentService.createShipment(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateShipment(@PathVariable("id") Integer shipmentId, @RequestBody @Valid ShipmentRequestDTO dto) {
        try {
            shipmentService.updateShipment(shipmentId, dto);
            return ResponseEntity.ok("Shipment updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listOne/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable Integer id) {
        try {
            ShipmentResponseDTO dto = shipmentService.getShipmentById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ShipmentResponseDTO>> getAllShipments() {
        List<ShipmentResponseDTO> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }
}