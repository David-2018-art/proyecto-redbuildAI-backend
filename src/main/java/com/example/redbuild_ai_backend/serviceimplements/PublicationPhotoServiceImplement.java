package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.PublicationPhotoDTO;
import com.example.redbuild_ai_backend.entities.Publication;
import com.example.redbuild_ai_backend.entities.PublicationPhoto;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IPublicationPhotoRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationPhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PublicationPhotoServiceImplement
        implements IPublicationPhotoService {

    private final IPublicationPhotoRepository pR;
    private final IPublicationRepository publicationRepository;

    public PublicationPhotoServiceImplement(
            IPublicationPhotoRepository pR,
            IPublicationRepository publicationRepository) {

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
    public List<PublicationPhotoDTO> getByPublicationId(
            Long publicationId) {

        return pR.findByPublicationId(publicationId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public PublicationPhotoDTO getById(Long id) {
        return convertToDTO(buscarFoto(id));
    }

    @Override
    @Transactional
    public PublicationPhotoDTO create(PublicationPhotoDTO dto) {

        if (dto.getIdPublication() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la publicación es obligatorio"
            );
        }

        Publication publication = publicationRepository
                .findById(dto.getIdPublication())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la publicación con ID: "
                                + dto.getIdPublication()
                ));

        verificarPermiso(publication);

        PublicationPhoto photo = new PublicationPhoto();

        photo.setUrlPhoto(dto.getUrlPhoto());
        photo.setDescriptionPhoto(dto.getDescriptionPhoto());
        photo.setUploadDate(dto.getUploadDate());
        photo.setPublication(publication);

        return convertToDTO(pR.save(photo));
    }

    @Override
    @Transactional
    public PublicationPhotoDTO update(
            Long id,
            PublicationPhotoDTO dto) {

        PublicationPhoto photo = buscarFoto(id);

        // Comprobar permisos sobre la publicación original.
        verificarPermiso(photo.getPublication());

        if (dto.getIdPublication() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la publicación es obligatorio"
            );
        }

        if (!Objects.equals(
                photo.getPublication().getId(),
                dto.getIdPublication())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite mover la foto a otra publicación"
            );
        }

        photo.setUrlPhoto(dto.getUrlPhoto());
        photo.setDescriptionPhoto(dto.getDescriptionPhoto());

        // Se conservan la publicación y la fecha de subida originales.

        return convertToDTO(pR.save(photo));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        PublicationPhoto photo = buscarFoto(id);

        verificarPermiso(photo.getPublication());

        pR.delete(photo);
    }

    private PublicationPhoto buscarFoto(Long id) {
        return pR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra la foto con ID: " + id
                ));
    }

    private void verificarPermiso(Publication publication) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Debes iniciar sesión"
            );
        }

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("Administrador")
                );

        boolean esPropietario = publication.getPublisher() != null
                && publication.getPublisher().getEmailUser() != null
                && publication.getPublisher().getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo el propietario de la publicación o un "
                            + "administrador puede gestionar sus fotos"
            );
        }
    }

    private PublicationPhotoDTO convertToDTO(
            PublicationPhoto photo) {

        return new PublicationPhotoDTO(
                photo.getIdPhoto(),
                photo.getUrlPhoto(),
                photo.getDescriptionPhoto(),
                photo.getUploadDate(),
                photo.getPublication().getId()
        );
    }
}