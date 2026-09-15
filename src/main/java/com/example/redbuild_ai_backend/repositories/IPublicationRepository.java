package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPublicationRepository
        extends JpaRepository<Publication, Long> {
}