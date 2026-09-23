package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;

import java.util.List;

public interface IPublicationService {

    List<PublicationDTO> getAll();
    List<PublicationDTO> findByStatus(String status);

    List<PublicationDTO> findByProductId(Long idProduct);

    PublicationDTO getById(Long id);

    PublicationDTO create(PublicationDTO publicationDTO);

    PublicationDTO update(Long id, PublicationDTO publicationDTO);

    void delete(Long id);
}