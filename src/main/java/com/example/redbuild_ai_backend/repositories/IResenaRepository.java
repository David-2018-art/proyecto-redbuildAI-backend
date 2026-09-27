package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenaRepository extends JpaRepository<Resena, Long> {

    @Query("SELECT r FROM Resena r JOIN FETCH r.user u WHERE u.idUser = :userId")
    List<Resena> findByUserId(@Param("userId") Long userId);
}
