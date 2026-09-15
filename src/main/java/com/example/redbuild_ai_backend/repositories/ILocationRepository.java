package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
}
