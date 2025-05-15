package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.entities.Shipment;

public interface ShipmentService {
    void createShipment(ShipmentRequestDTO dto, Integer userId);
}