package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.PublicationPhotoDTO;
import com.example.redbuild_ai_backend.entities.Publication;
import com.example.redbuild_ai_backend.entities.PublicationPhoto;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IPublicationPhotoRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationPhotoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationPhotoServiceImplement implements IPublicationPhotoService {
    private final IPublicationPhotoRepository pR;
    private final IPublicationRepository publicationRepository;

    public PublicationPhotoServiceImplement(IPublicationPhotoRepository pR, IPublicationRepository publicationRepository) {
        this.pR = pR;
        this.publicationRepository = publicationRepository;
    }


    @Override
    public List<PublicationPhotoDTO> getAll() {
        return pR.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public PublicationPhotoDTO getById(Long id) {
        PublicationPhoto photo = pR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la foto con ID: " + id));
        return convertToDTO(photo);
    }

    @Override
    public PublicationPhotoDTO create(PublicationPhotoDTO publicationPhotoDTO) {
        Publication publication = publicationRepository
                .findById(publicationPhotoDTO.getIdPublication())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la publicacion con ID: "
                                + publicationPhotoDTO.getIdPublication()));
        PublicationPhoto photo = new PublicationPhoto();

        photo.setUrlPhoto(publicationPhotoDTO.getUrlPhoto());
        photo.setDescriptionPhoto(publicationPhotoDTO.getDescriptionPhoto());
        photo.setUploadDate(publicationPhotoDTO.getUploadDate());
        photo.setPublication(publication);

        PublicationPhoto savedPhoto = pR.save(photo);
        return convertToDTO(savedPhoto);
    }

    @Override
    public PublicationPhotoDTO update(Long id, PublicationPhotoDTO publicationPhotoDTO) {
        PublicationPhoto photo = pR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la foto con ID: " + id));
        Publication publication = publicationRepository
                .findById(publicationPhotoDTO.getIdPublication())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la publicacion con ID: "
                                + publicationPhotoDTO.getIdPublication()));

        photo.setUrlPhoto(publicationPhotoDTO.getUrlPhoto());
        photo.setDescriptionPhoto(publicationPhotoDTO.getDescriptionPhoto());
        photo.setUploadDate(publicationPhotoDTO.getUploadDate());
        photo.setPublication(publication);

        PublicationPhoto updatedPhoto = pR.save(photo);

        return convertToDTO(updatedPhoto);
    }

    @Override
    public void delete(Long id) {
        PublicationPhoto photo = pR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la foto con ID: " + id));
        pR.delete(photo);
    }

    private PublicationPhotoDTO convertToDTO(PublicationPhoto photo) {
        return new PublicationPhotoDTO(
                photo.getIdPhoto(),
                photo.getUrlPhoto(),
                photo.getDescriptionPhoto(),
                photo.getUploadDate(),
                photo.getPublication().getId()
        );
    }
}
