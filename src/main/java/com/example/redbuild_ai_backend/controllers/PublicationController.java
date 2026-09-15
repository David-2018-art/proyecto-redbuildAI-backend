package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@Slf4j
public class PublicationController {

    private final IPublicationService publicationService;

    public PublicationController(
            IPublicationService publicationService) {

        this.publicationService = publicationService;
    }

    @GetMapping
    public List<PublicationDTO> getAll() {

        log.info(
                "Solicitud GET para obtener publicaciones"
        );

        return publicationService.getAll();
    }

    @GetMapping("/{id}")
    public PublicationDTO getById(
            @PathVariable Long id) {

        log.info(
                "Solicitud GET para obtener publicación con ID: {}",
                id
        );

        return publicationService.getById(id);
    }

    @PostMapping
    public PublicationDTO create(
            @RequestBody PublicationDTO publicationDTO) {

        log.info(
                "Solicitud POST para registrar publicación"
        );

        return publicationService.create(
                publicationDTO
        );
    }

    @PutMapping("/{id}")
    public PublicationDTO update(
            @PathVariable Long id,
            @RequestBody PublicationDTO publicationDTO) {

        log.info(
                "Solicitud PUT para actualizar publicación con ID: {}",
                id
        );

        return publicationService.update(
                id,
                publicationDTO
        );
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        log.warn(
                "Solicitud DELETE para eliminar publicación con ID: {}",
                id
        );

        publicationService.delete(id);
    }
}