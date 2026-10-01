package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransaccionRepository
        extends JpaRepository<Transaccion, Long> {

    @Query("""
            SELECT t
            FROM Transaccion t
            JOIN FETCH t.user u
            JOIN FETCH t.publication
            WHERE u.idUser = :userId
            ORDER BY t.dateRegisterTransaction DESC,
                     t.idTransaccion DESC
            """)
    List<Transaccion> findTransactionsByUserId(
            @Param("userId") Long userId
    );

    boolean existsByPublication_Id(Long publicationId);

    boolean existsByUser_IdUser(Long idUser);

    @Query("""
        SELECT t.typeTransaction,
               COUNT(t),
               SUM(t.amountTransaction)
        FROM Transaccion t
        JOIN t.publication p
        JOIN p.publisher u
        WHERE u.idUser = :idEmpresa
          AND t.statusTransaction = 'Completada'
        GROUP BY t.typeTransaction
        """)
    List<Object[]> resumenPorEmpresa(
            @Param("idEmpresa") Long idEmpresa
    );
}