package com.couriersync.backendenvios.services;

import com.couriersync.backendenvios.dtos.ShipmentCreationRequestDTO; // Nueva importación
import com.couriersync.backendenvios.dtos.ShipmentUpdateRequestDTO;
import com.couriersync.backendenvios.dtos.ShipmentResponseDTO;
import com.couriersync.backendenvios.dtos.ShipmentSummaryResponseDTO;

import java.util.List;

public interface ShipmentService {
    void createShipment(ShipmentCreationRequestDTO dto, Integer userId); // Firma del método actualizada
    void updateShipment(Integer shipmentId, ShipmentUpdateRequestDTO dto);
    ShipmentResponseDTO getShipmentById(Integer id);
    List<ShipmentResponseDTO> getAllShipments();
    void updateShipmentStatusToInTransit(Integer shipmentId);
    void updateShipmentStatusToDelivered(Integer shipmentId);
    ShipmentSummaryResponseDTO getShipmentSummaryForAdmin();
}