package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query(value = "SELECT t.*, u.idUser AS userId, u.nameUser AS userName, u.emailUser AS userEmail " +
            "FROM Transacciones t JOIN Usuarios u ON t.id_user = u.idUser " +
            "WHERE u.idUser = :userId", nativeQuery = true)
    List<Object[]> findTransactionsByUserId(@Param("userId") Long userId);
}
