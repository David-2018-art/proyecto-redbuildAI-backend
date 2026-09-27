package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.dtos.PublicationPhotoDTO;

import java.util.List;

public interface IPublicationPhotoService {
    List<PublicationPhotoDTO> getAll();
    List<PublicationPhotoDTO> getByPublicationId(Long publicationId);
    PublicationPhotoDTO getById(Long id);
    PublicationPhotoDTO create(PublicationPhotoDTO publicationPhotoDTO);
    PublicationPhotoDTO update(Long id, PublicationPhotoDTO publicationPhotoDTO);
    void delete(Long id);
}
