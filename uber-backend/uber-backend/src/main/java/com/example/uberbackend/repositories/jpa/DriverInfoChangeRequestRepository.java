package com.example.uberbackend.repositories.jpa;

import com.example.uberbackend.model.DriverInfoChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverInfoChangeRequestRepository extends JpaRepository<DriverInfoChangeRequest, Long> {
}
