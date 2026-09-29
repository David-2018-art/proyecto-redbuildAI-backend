package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;
import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IProductService;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/publications")
@PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
public class PublicationController {

    private final IPublicationService publicationService;
    private final IProductService productService;
    private final IUserService userService;

    public PublicationController(
            IPublicationService publicationService,
            IProductService productService,
            IUserService userService) {

        this.publicationService = publicationService;
        this.productService = productService;
        this.userService = userService;
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAll() {

        return ResponseEntity.ok(publicationService.getAll());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getById(
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(publicationService.getById(id));
    }

    // CONSULTA: BUSCAR POR ESTADO
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PublicationDTO>> findByStatus(
            @PathVariable("status") String status) {

        return ResponseEntity.ok(
                publicationService.findByStatus(status)
        );
    }

    // CONSULTA: CONTAR PUBLICACIONES POR PRODUCTO
    @GetMapping("/product/{idProduct}/count")
    public ResponseEntity<Long> countByProductId(
            @PathVariable("idProduct") Long idProduct) {

        return ResponseEntity.ok(
                publicationService.countByProductId(idProduct)
        );
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<PublicationDTO> create(
            @Valid @RequestBody PublicationDTO dto,
            Authentication authentication) {

        validarIds(dto);

        Product product = productService.listId(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID: "
                                + dto.getProductId()
                ));

        User propietario = product.getUser();

        verificarPermiso(propietario, authentication);

        if (!Objects.equals(
                propietario.getIdUser(),
                dto.getPublisherUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El publicador debe ser el propietario del producto"
            );
        }

        PublicationDTO creada = publicationService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creada);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody PublicationDTO dto,
            Authentication authentication) {

        PublicationDTO existente = publicationService.getById(id);

        User publicador = buscarPublicador(existente);

        verificarPermiso(publicador, authentication);
        validarIds(dto);

        if (!Objects.equals(
                existente.getPublisherUserId(),
                dto.getPublisherUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el publicador"
            );
        }

        if (!Objects.equals(
                existente.getProductId(),
                dto.getProductId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el producto de la publicación"
            );
        }

        // Conserva la fecha original.
        dto.setPublicationDate(existente.getPublicationDate());

        return ResponseEntity.ok(
                publicationService.update(id, dto)
        );
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id,
            Authentication authentication) {

        PublicationDTO existente = publicationService.getById(id);

        User publicador = buscarPublicador(existente);

        verificarPermiso(publicador, authentication);

        publicationService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // BUSCAR AL PUBLICADOR GUARDADO
    private User buscarPublicador(PublicationDTO publication) {

        return userService.listId(publication.getPublisherUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario publicador"
                ));
    }

    // COMPROBAR PROPIETARIO O ADMINISTRADOR
    private void verificarPermiso(
            User propietario,
            Authentication authentication) {

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("Administrador")
                );

        boolean esPropietario = propietario.getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para realizar esta operación"
            );
        }
    }

    // VALIDAR LOS IDENTIFICADORES OBLIGATORIOS
    private void validarIds(PublicationDTO dto) {

        if (dto.getProductId() == null
                || dto.getPublisherUserId() == null
                || dto.getLocationId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El producto, el publicador y la ubicación son obligatorios"
            );
        }
    }
}