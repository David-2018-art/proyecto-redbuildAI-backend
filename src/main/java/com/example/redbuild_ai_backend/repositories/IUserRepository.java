package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByRole_IdRole(Long idRole);
    // Query simple: filtra usuarios directamente por su estado.
    @Query("SELECT u FROM User u WHERE u.statusUser = :statusUser")
    List<User> findByStatusUser(@Param("statusUser") String statusUser);

    // Query con JOIN: obtiene usuarios cuyo rol tiene el nombre indicado.
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.nameRole = :nameRole")
    List<User> findByRoleName(@Param("nameRole") String nameRole);
    Optional<User> findByEmailUser(String emailUser);

}
