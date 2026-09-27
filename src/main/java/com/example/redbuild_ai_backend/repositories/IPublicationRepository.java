package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPublicationRepository
        extends JpaRepository<Publication, Long> {

    // Buscar publicaciones por estado
    List<Publication> findByStatusIgnoreCase(String status);

    // Contar publicaciones asociadas a un producto
    long countByProduct_IdProduct(Long idProduct);
}