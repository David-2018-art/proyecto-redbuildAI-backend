package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenaRepository
        extends JpaRepository<Resena, Long> {

    @Query("""
            SELECT r
            FROM Resena r
            JOIN FETCH r.user u
            JOIN FETCH r.ratedUser
            JOIN FETCH r.transaccion
            WHERE u.idUser = :userId
            ORDER BY r.dateRegisterResena DESC, r.idResena DESC
            """)
    List<Resena> findByUserId(@Param("userId") Long userId);

    boolean existsByTransaccion_IdTransaccionAndUser_IdUser(
            Long idTransaccion,
            Long idUser
    );

    boolean existsByTransaccion_IdTransaccion(Long idTransaccion);

    boolean existsByUser_IdUserOrRatedUser_IdUser(
            Long idAutor,
            Long idCalificado
    );
}