package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
