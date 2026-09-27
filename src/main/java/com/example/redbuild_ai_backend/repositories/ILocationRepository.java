package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.department = :department")
    List<Location> findByDepartment(@Param("department") String departmente);
}
