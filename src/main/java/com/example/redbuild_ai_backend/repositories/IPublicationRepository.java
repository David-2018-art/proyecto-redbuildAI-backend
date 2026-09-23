package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPublicationRepository
        extends JpaRepository<Publication, Long> {

    List<Publication> findByStatusIgnoreCase(String status);

    List<Publication> findByProduct_IdProduct(Long IdProduct);
}