package com.example.uberbackend.repositories.jpa;

import com.example.uberbackend.model.FavoriteRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRouteRepository extends JpaRepository<FavoriteRoute, Long> {
}
