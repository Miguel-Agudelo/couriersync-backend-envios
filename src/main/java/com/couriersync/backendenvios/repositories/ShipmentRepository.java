package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    Long countByStatus_Name(String statusName);

    @Query("SELECT COUNT(s) FROM Shipment s WHERE s.status.name != 'entregado' AND s.deliveryDate < CURRENT_DATE")
    Long countDelayedShipments();
}