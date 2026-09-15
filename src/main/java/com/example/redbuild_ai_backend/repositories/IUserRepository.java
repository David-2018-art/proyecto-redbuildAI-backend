package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByRole_IdRole(Long idRole);
}
