package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.ResenaDTO;
import com.example.redbuild_ai_backend.entities.Resena;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IResenaService;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/Resenas")
@Tag(name = "Reseñas", description = "Reseñas entre usuarios de una transacción")
@PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
public class ResenaController {

    private final IResenaService rS;
    private final IUserService uS;
    private final ITransaccionService tS;

    public ResenaController(
            IResenaService rS,
            IUserService uS,
            ITransaccionService tS) {

        this.rS = rS;
        this.uS = uS;
        this.tS = tS;
    }

    @GetMapping
    public ResponseEntity<List<ResenaDTO>> listar() {
        return ResponseEntity.ok(
                rS.list().stream()
                        .map(this::convertirDTO)
                        .toList()
        );
    }

    // Reseñas escritas por el usuario indicado.
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ResenaDTO>> listarPorUsuario(
            @PathVariable("userId") Long userId) {

        return ResponseEntity.ok(
                rS.findByUserId(userId).stream()
                        .map(this::convertirDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> buscarId(
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(convertirDTO(buscarResena(id)));
    }

    @PostMapping
    public ResponseEntity<ResenaDTO> registrar(
            @Valid @RequestBody ResenaDTO dto,
            Authentication authentication) {

        User autor = buscarUsuario(dto.getIdUser());

        verificarAutor(autor, authentication);

        User calificado = buscarUsuario(dto.getIdRatedUser());

        Transaccion transaccion = tS.listId(dto.getIdTransaccion())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la transacción con ID: "
                                + dto.getIdTransaccion()
                ));

        Resena resena = new Resena();

        copiarContenido(dto, resena);
        resena.setUser(autor);
        resena.setRatedUser(calificado);
        resena.setTransaccion(transaccion);

        rS.insert(resena);

        Resena guardada = buscarResena(resena.getIdResena());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(guardada.getIdResena())
                .toUri();

        return ResponseEntity.created(location)
                .body(convertirDTO(guardada));
    }

    @PutMapping
    public ResponseEntity<ResenaDTO> actualizar(
            @Valid @RequestBody ResenaDTO dto,
            Authentication authentication) {

        if (dto.getIdResena() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la reseña es obligatorio para actualizar"
            );
        }

        Resena original = buscarResena(dto.getIdResena());

        verificarAutor(original.getUser(), authentication);

        if (!Objects.equals(
                original.getUser().getIdUser(), dto.getIdUser())
                || !Objects.equals(
                original.getRatedUser().getIdUser(), dto.getIdRatedUser())
                || !Objects.equals(
                original.getTransaccion().getIdTransaccion(),
                dto.getIdTransaccion())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el autor, "
                            + "el usuario calificado ni la transacción"
            );
        }

        // No modificar la entidad original antes de validar en el servicio.
        Resena cambios = new Resena();

        cambios.setIdResena(original.getIdResena());
        cambios.setUser(original.getUser());
        cambios.setRatedUser(original.getRatedUser());
        cambios.setTransaccion(original.getTransaccion());
        copiarContenido(dto, cambios);

        rS.update(cambios);

        return ResponseEntity.ok(
                convertirDTO(buscarResena(original.getIdResena()))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable("id") Long id,
            Authentication authentication) {

        Resena resena = buscarResena(id);

        verificarPermiso(resena.getUser(), authentication);

        rS.delete(id);

        return ResponseEntity.ok("Reseña eliminada correctamente");
    }

    private Resena buscarResena(Long id) {
        return rS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la reseña con ID: " + id
                ));
    }

    private User buscarUsuario(Long id) {
        return uS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con ID: " + id
                ));
    }

    private void verificarPermiso(
            User autor,
            Authentication authentication) {

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        "Administrador".equals(a.getAuthority())
                );

        boolean esAutor = authentication.getName()
                .equalsIgnoreCase(autor.getEmailUser());

        if (!esAdministrador && !esAutor) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo el autor o un administrador "
                            + "puede gestionar esta reseña"
            );
        }
    }

    private void copiarContenido(ResenaDTO dto, Resena resena) {
        resena.setTitleResena(dto.getTitleResena());
        resena.setDescriptionResena(dto.getDescriptionResena());
        resena.setScoreResena(dto.getScoreResena());
        resena.setStatusResena(dto.getStatusResena());
    }

    private ResenaDTO convertirDTO(Resena resena) {

        ResenaDTO dto = new ResenaDTO();

        dto.setIdResena(resena.getIdResena());
        dto.setTitleResena(resena.getTitleResena());
        dto.setDescriptionResena(resena.getDescriptionResena());
        dto.setScoreResena(resena.getScoreResena());
        dto.setDateRegisterResena(resena.getDateRegisterResena());
        dto.setStatusResena(resena.isStatusResena());
        dto.setIdUser(resena.getUser().getIdUser());
        dto.setIdRatedUser(resena.getRatedUser().getIdUser());
        dto.setIdTransaccion(
                resena.getTransaccion().getIdTransaccion()
        );

        return dto;
    }

    private void verificarAutor(
            User autor,
            Authentication authentication) {

        if (!authentication.getName()
                .equalsIgnoreCase(autor.getEmailUser())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo el autor puede registrar o editar su reseña"
            );
        }
    }
}