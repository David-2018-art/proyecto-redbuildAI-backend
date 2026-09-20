package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByStatusRole(String statusRole);
}
