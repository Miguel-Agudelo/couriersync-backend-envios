package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
