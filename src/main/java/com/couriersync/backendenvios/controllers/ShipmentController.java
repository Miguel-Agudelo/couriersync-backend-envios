package com.couriersync.backendenvios.controllers;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}