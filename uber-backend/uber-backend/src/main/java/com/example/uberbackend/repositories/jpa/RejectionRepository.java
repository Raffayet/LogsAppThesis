package com.example.uberbackend.repositories.jpa;
import com.example.uberbackend.model.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectionRepository extends JpaRepository<Rejection, Long> {
}
