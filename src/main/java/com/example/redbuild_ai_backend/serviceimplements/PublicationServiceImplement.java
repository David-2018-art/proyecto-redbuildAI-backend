package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;
import com.example.redbuild_ai_backend.entities.Location;
import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.entities.Publication;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.*;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PublicationServiceImplement
        implements IPublicationService {

    private final IPublicationRepository publicationRepository;
    private final IProductRepository productRepository;
    private final ILocationRepository locationRepository;
    private final IUserRepository userRepository;
    private final IPublicationPhotoRepository photoRepository;
    private final ITransaccionRepository transaccionRepository;

    public PublicationServiceImplement(
            IPublicationRepository publicationRepository,
            IProductRepository productRepository,
            ILocationRepository locationRepository,
            IUserRepository userRepository,
            IPublicationPhotoRepository photoRepository,
            ITransaccionRepository transaccionRepository) {

        this.publicationRepository = publicationRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.transaccionRepository = transaccionRepository;
    }
    @Override
    public List<PublicationDTO> getAll() {

        log.info("Obteniendo lista de publicaciones");

        return publicationRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<PublicationDTO> findByStatus(String status) {

        log.info(
                "Buscando publicaciones por estado: {}",
                status
        );

        return publicationRepository
                .findByStatusIgnoreCase(status)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public long countByProductId(Long idProduct) {

        log.info(
                "Contando publicaciones del producto con ID: {}",
                idProduct
        );

        return publicationRepository
                .countByProduct_IdProduct(idProduct);
    }

    @Override
    public PublicationDTO getById(Long id) {

        log.info(
                "Buscando publicación con ID: {}",
                id
        );

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

        Location location = locationRepository
                .findById(publicationDTO.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ubicación no encontrada con ID: "
                                        + publicationDTO.getLocationId()
                        )
                );

        User publisher = userRepository
                .findById(publicationDTO.getPublisherUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con ID: "
                                        + publicationDTO.getPublisherUserId()
                        )
                );

        Publication publication = new Publication();

        publication.setTitle(
                publicationDTO.getTitle()
        );

        publication.setObservations(
                publicationDTO.getObservations()
        );

        publication.setOperationType(
                publicationDTO.getOperationType()
        );

        publication.setPublicationDate(LocalDateTime.now());

        publication.setStatus(
                publicationDTO.getStatus()
        );

        publication.setProduct(product);
        publication.setLocation(location);
        publication.setPublisher(publisher);

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

        Location location = locationRepository
                .findById(publicationDTO.getLocationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ubicación no encontrada con ID: "
                                        + publicationDTO.getLocationId()
                        )
                );

        User publisher = userRepository
                .findById(publicationDTO.getPublisherUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con ID: "
                                        + publicationDTO.getPublisherUserId()
                        )
                );

        publication.setTitle(
                publicationDTO.getTitle()
        );

        publication.setObservations(
                publicationDTO.getObservations()
        );

        publication.setOperationType(
                publicationDTO.getOperationType()
        );

        publication.setStatus(
                publicationDTO.getStatus()
        );

        publication.setProduct(product);
        publication.setLocation(location);
        publication.setPublisher(publisher);

        Publication updatedPublication =
                publicationRepository.save(publication);

        return convertToDTO(updatedPublication);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Publicación no encontrada con ID: " + id
                ));

        if (photoRepository.existsByPublication_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la publicación porque "
                            + "tiene fotos asociadas"
            );
        }

        if (transaccionRepository.existsByPublication_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la publicación porque "
                            + "tiene transacciones asociadas"
            );
        }

        try {
            publicationRepository.delete(publication);
            publicationRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la publicación porque "
                            + "tiene registros asociados",
                    exception
            );
        }
    }

    private PublicationDTO convertToDTO(
            Publication publication) {

        PublicationDTO publicationDTO =
                new PublicationDTO();

        publicationDTO.setId(
                publication.getId()
        );

        publicationDTO.setTitle(
                publication.getTitle()
        );

        publicationDTO.setObservations(
                publication.getObservations()
        );

        publicationDTO.setOperationType(
                publication.getOperationType()
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

        publicationDTO.setLocationId(
                publication.getLocation().getIdLocation()
        );

        publicationDTO.setPublisherUserId(
                publication.getPublisher().getIdUser()
        );

        return publicationDTO;
    }
}