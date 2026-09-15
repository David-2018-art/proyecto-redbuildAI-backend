package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;
import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.entities.Publication;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IProductRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PublicationServiceImplement
        implements IPublicationService {

    private final IPublicationRepository publicationRepository;
    private final IProductRepository productRepository;

    public PublicationServiceImplement(
            IPublicationRepository publicationRepository,
            IProductRepository productRepository) {

        this.publicationRepository = publicationRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<PublicationDTO> getAll() {

        log.info("Obteniendo lista de publicaciones");

        return publicationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public PublicationDTO getById(Long id) {

        log.info("Buscando publicación con ID: {}", id);

        Publication publication = publicationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Publicación no encontrada con ID: " + id
                        )
                );

        return convertToDTO(publication);
    }

    @Override
    public PublicationDTO create(
            PublicationDTO publicationDTO) {

        log.info("Registrando nueva publicación");

        Product product = productRepository
                .findById(publicationDTO.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con ID: "
                                        + publicationDTO.getProductId()
                        )
                );

        Publication publication = new Publication();

        publication.setPublicationDate(
                publicationDTO.getPublicationDate()
        );

        publication.setStatus(
                publicationDTO.getStatus()
        );

        publication.setProduct(product);

        Publication savedPublication =
                publicationRepository.save(publication);

        return convertToDTO(savedPublication);
    }

    @Override
    public PublicationDTO update(
            Long id,
            PublicationDTO publicationDTO) {

        log.info(
                "Actualizando publicación con ID: {}",
                id
        );

        Publication publication = publicationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Publicación no encontrada con ID: " + id
                        )
                );

        Product product = productRepository
                .findById(publicationDTO.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con ID: "
                                        + publicationDTO.getProductId()
                        )
                );

        publication.setPublicationDate(
                publicationDTO.getPublicationDate()
        );

        publication.setStatus(
                publicationDTO.getStatus()
        );

        publication.setProduct(product);

        Publication updatedPublication =
                publicationRepository.save(publication);

        return convertToDTO(updatedPublication);
    }

    @Override
    public void delete(Long id) {

        log.warn(
                "Eliminando publicación con ID: {}",
                id
        );

        Publication publication = publicationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Publicación no encontrada con ID: " + id
                        )
                );

        publicationRepository.delete(publication);
    }

    private PublicationDTO convertToDTO(
            Publication publication) {

        PublicationDTO publicationDTO =
                new PublicationDTO();

        publicationDTO.setId(
                publication.getId()
        );

        publicationDTO.setPublicationDate(
                publication.getPublicationDate()
        );

        publicationDTO.setStatus(
                publication.getStatus()
        );

        publicationDTO.setProductId(
                publication.getProduct().getIdProduct()
        );

        return publicationDTO;
    }
}