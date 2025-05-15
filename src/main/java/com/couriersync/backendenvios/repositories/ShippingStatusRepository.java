package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Integer> {
    Optional<ShippingStatus> findByName(String name);
}
