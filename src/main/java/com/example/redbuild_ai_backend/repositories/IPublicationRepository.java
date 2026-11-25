package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPublicationRepository
        extends JpaRepository<Publication, Long> {

    // Consulta simple: buscar por estado.
    List<Publication> findByStatusIgnoreCase(String status);

    // Consulta con JOIN: contar publicaciones de un producto.
    @Query("""
            SELECT COUNT(p)
            FROM Publication p
            JOIN p.product producto
            WHERE producto.IdProduct = :idProduct
            """)
    long countByProduct_IdProduct(
            @Param("idProduct") Long idProduct
    );

    boolean existsByLocation_IdLocation(Long idLocation);

    boolean existsByPublisher_IdUser(Long idUser);
}