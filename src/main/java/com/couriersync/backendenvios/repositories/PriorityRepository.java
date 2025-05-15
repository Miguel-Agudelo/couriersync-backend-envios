package com.couriersync.backendenvios.repositories;

import com.couriersync.backendenvios.entities.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}
