package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ShipmentRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentSummaryResponseDTO;
import com.couriersync.backendenvios.entities.Shipment;

import java.util.List;

public interface ShipmentService {
    void createShipment(ShipmentRequestDTO dto, Integer userId);
    void updateShipment(Integer shipmentId, ShipmentRequestDTO dto);
    ShipmentResponseDTO getShipmentById(Integer id);
    List<ShipmentResponseDTO> getAllShipments();
    void updateShipmentStatusToInTransit(Integer shipmentId);
    void updateShipmentStatusToDelivered(Integer shipmentId);
    ShipmentSummaryResponseDTO getShipmentSummaryForAdmin();
}