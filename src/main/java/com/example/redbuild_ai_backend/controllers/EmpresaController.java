package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.EmpresaResumenDTO;
import com.example.redbuild_ai_backend.serviceimplements.EmpresaReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empresas")
@Tag(
        name = "Reportes empresariales",
        description = "Resumen de ventas y donaciones de la empresa autenticada"
)
@PreAuthorize("hasAuthority('Empresa')")
public class EmpresaController {

    private final EmpresaReporteService reporteService;

    public EmpresaController(EmpresaReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/mi-resumen")
    @Operation(
            summary = "Consultar mi resumen empresarial",
            description = "Cuenta ventas y donaciones completadas "
                    + "de las publicaciones de la empresa autenticada."
    )
    public ResponseEntity<EmpresaResumenDTO> miResumen(
            Authentication authentication) {

        return ResponseEntity.ok(
                reporteService.obtenerMiResumen(
                        authentication.getName()
                )
        );
    }
}
