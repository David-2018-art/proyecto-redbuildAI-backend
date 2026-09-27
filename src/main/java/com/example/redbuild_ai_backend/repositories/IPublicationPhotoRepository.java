package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.PublicationPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPublicationPhotoRepository extends JpaRepository<PublicationPhoto,Long> {
    @Query("SELECT pp FROM PublicationPhoto pp JOIN pp.publication p WHERE p.id = :publicationId")
    List<PublicationPhoto> findByPublicationId(@Param("publicationId") Long publicationId);
}
