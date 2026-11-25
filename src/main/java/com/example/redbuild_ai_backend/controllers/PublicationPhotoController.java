package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.PublicationPhotoDTO;
import com.example.redbuild_ai_backend.serviceinterfaces.IPublicationPhotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/publication-photos")
public class PublicationPhotoController {
    private final IPublicationPhotoService publicationPhotoService;

    public PublicationPhotoController(IPublicationPhotoService publicationPhotoService) {
        this.publicationPhotoService = publicationPhotoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<PublicationPhotoDTO>> getAll(){
        return ResponseEntity.ok(publicationPhotoService.getAll());
    }

    @GetMapping("/publication/{publicationId}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<PublicationPhotoDTO>> getByPublicationId(
            @PathVariable Long publicationId) {
        return ResponseEntity.ok(
                publicationPhotoService.getByPublicationId(publicationId)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<PublicationPhotoDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(publicationPhotoService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<PublicationPhotoDTO> create(@Valid @RequestBody PublicationPhotoDTO publicationPhotoDTO){

        PublicationPhotoDTO createdPhoto =
                publicationPhotoService.create(publicationPhotoDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPhoto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<PublicationPhotoDTO> update(@PathVariable Long id, @Valid @RequestBody PublicationPhotoDTO publicationPhotoDTO) {

        return ResponseEntity.ok(publicationPhotoService.update(id, publicationPhotoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        publicationPhotoService.delete(id);
        return ResponseEntity.ok("Foto eliminada correctamente");
    }
}

