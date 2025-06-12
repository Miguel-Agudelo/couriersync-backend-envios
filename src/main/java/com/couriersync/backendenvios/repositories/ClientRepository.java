package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email); // Útil para validación de unicidad
}