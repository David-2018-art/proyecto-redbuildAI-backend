package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.PublicationDTO;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    private final IPublicationService publicationService;

    public PublicationController(
            IPublicationService publicationService) {

        this.publicationService = publicationService;
    }

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAll() {

        return ResponseEntity.ok(
                publicationService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                publicationService.getById(id)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PublicationDTO>> findByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                publicationService.findByStatus(status)
        );
    }

    @GetMapping("/product/{idProduct}/count")
    public ResponseEntity<Long> countByProductId(
            @PathVariable Long idProduct) {

        return ResponseEntity.ok(
                publicationService.countByProductId(idProduct)
        );
    }

    @PostMapping
    public ResponseEntity<PublicationDTO> create(
            @RequestBody PublicationDTO publicationDTO) {

        PublicationDTO publication =
                publicationService.create(publicationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(publication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> update(
            @PathVariable Long id,
            @RequestBody PublicationDTO publicationDTO) {

        return ResponseEntity.ok(
                publicationService.update(
                        id,
                        publicationDTO
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        publicationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}