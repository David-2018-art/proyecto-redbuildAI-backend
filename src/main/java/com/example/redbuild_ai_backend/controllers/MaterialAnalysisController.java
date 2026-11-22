package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.MaterialAnalysisDTO;
import com.example.redbuild_ai_backend.serviceimplements.MaterialAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/material-analysis")
public class MaterialAnalysisController {

    private final MaterialAnalysisService service;

    public MaterialAnalysisController(MaterialAnalysisService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MaterialAnalysisDTO analizar(
            @RequestParam("foto") MultipartFile foto
    ) {
        return service.analizar(foto);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarError(
            ResponseStatusException ex
    ) {
        return ResponseEntity.status(ex.getStatusCode()).body(
                Map.of(
                        "status", ex.getStatusCode().value(),
                        "message", ex.getReason() == null
                                ? "No se pudo analizar la foto."
                                : ex.getReason()
                )
        );
    }
}