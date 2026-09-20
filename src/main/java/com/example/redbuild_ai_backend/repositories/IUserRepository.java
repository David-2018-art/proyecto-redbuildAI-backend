package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByRole_IdRole(Long idRole);
    List<User> findByStatusUser(String statusUser);

    // Query simple: filtra usuarios directamente por su estado.
    // Query con JOIN: obtiene usuarios cuyo rol tiene el nombre indicado.
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.nameRole = :nameRole")
    List<User> findByRoleName(@Param("nameRole") String nameRole);

}
