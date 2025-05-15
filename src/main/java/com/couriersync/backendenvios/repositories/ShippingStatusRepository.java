package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Integer> {
}
