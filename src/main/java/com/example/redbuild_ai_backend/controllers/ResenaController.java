package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.ResenaDTO;
import com.example.redbuild_ai_backend.entities.Resena;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IResenaService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Resenas")
@Tag(name = "Reseñas", description = "Endpoints para gestionar reseñas de usuarios")
public class ResenaController {

    private final IResenaService rS;
    private final IUserService uS;
    private final ModelMapper modelMapper;

    public ResenaController(IResenaService rS, IUserService uS, ModelMapper modelMapper) {
        this.rS = rS;
        this.uS = uS;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Listar reseñas", description = "Obtiene todas las reseñas registradas.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<ResenaDTO>> listar() {
        List<ResenaDTO> lista = rS.list()
                .stream()
                .map(r -> {
                    ResenaDTO dto = modelMapper.map(r, ResenaDTO.class);
                    dto.setIdUser(r.getUser().getIdUser());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar reseñas por usuario", description = "Obtiene todas las reseñas relacionadas a un usuario específico mediante el query JOIN del repositorio.")
    @GetMapping("/usuario/{userId}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<ResenaDTO>> listarPorUsuario(@PathVariable Long userId) {
        List<ResenaDTO> lista = rS.findByUserId(userId)
                .stream()
                .map(r -> {
                    ResenaDTO dto = modelMapper.map(r, ResenaDTO.class);
                    dto.setIdUser(r.getUser().getIdUser());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Registrar reseña", description = "Crea una reseña asociada a un usuario. Ejemplo de cuerpo: {\"titleResena\":\"Muy buena atención\",\"descriptionResena\":\"El servicio fue rápido y la atención fue excelente.\",\"scoreResena\":5,\"statusResena\":true,\"idUser\":7}")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<ResenaDTO> registrar(
            @Valid @RequestBody ResenaDTO dto,
            Authentication authentication) {

        User user = uS.listId(dto.getIdUser())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe el usuario con ID: " + dto.getIdUser()
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = user.getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes registrar reseñas para otro usuario"
            );
        }

        Resena resena = modelMapper.map(dto, Resena.class);
        resena.setIdResena(null);
        resena.setDateRegisterResena(LocalDateTime.now());
        resena.setUser(user);

        rS.insert(resena);

        ResenaDTO responseDTO = modelMapper.map(resena, ResenaDTO.class);
        responseDTO.setIdUser(resena.getUser().getIdUser());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resena.getIdResena())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @Operation(summary = "Buscar reseña por ID", description = "Retorna la reseña según el identificador enviado.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<ResenaDTO> buscarId(@PathVariable Long id) {
        Resena resena = rS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la reseña con ID: " + id));

        ResenaDTO dto = modelMapper.map(resena, ResenaDTO.class);
        dto.setIdUser(resena.getUser().getIdUser());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Actualizar reseña", description = "Actualiza una reseña existente. Ejemplo de cuerpo: {\"idResena\":1,\"titleResena\":\"Muy buena atención\",\"descriptionResena\":\"La atención mejoró y el servicio fue excelente.\",\"scoreResena\":5,\"statusResena\":true,\"idUser\":7}")
    @PutMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<ResenaDTO> actualizar(
            @Valid @RequestBody ResenaDTO dto,
            Authentication authentication) {

        if (dto.getIdResena() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la reseña es obligatorio para actualizar"
            );
        }

        Resena resena = rS.listId(dto.getIdResena())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe la reseña con ID: " + dto.getIdResena()
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = resena.getUser()
                .getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para actualizar esta reseña"
            );
        }

        if (!resena.getUser().getIdUser().equals(dto.getIdUser())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el propietario de la reseña"
            );
        }

        resena.setTitleResena(dto.getTitleResena());
        resena.setDescriptionResena(dto.getDescriptionResena());
        resena.setScoreResena(dto.getScoreResena());
        resena.setStatusResena(dto.isStatusResena());

        rS.update(resena);

        ResenaDTO responseDTO = modelMapper.map(resena, ResenaDTO.class);
        responseDTO.setIdUser(resena.getUser().getIdUser());

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Eliminar reseña", description = "Elimina la reseña según su ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id,
            Authentication authentication) {

        Resena resena = rS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe la reseña con ID: " + id
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = resena.getUser()
                .getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para eliminar esta reseña"
            );
        }

        rS.delete(resena.getIdResena());

        return ResponseEntity.ok("Reseña eliminada correctamente");
    }
}
