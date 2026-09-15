package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.PublicationPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicationPhotoRepository extends JpaRepository<PublicationPhoto,Long> {
}
