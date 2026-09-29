package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.TransaccionDTO;
import com.example.redbuild_ai_backend.dtos.TransaccionUsuarioDTO;
import com.example.redbuild_ai_backend.entities.Publication;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/Transacciones")
@Tag(
        name = "Transacciones",
        description = "Gestiona solicitudes de compra y donación"
)
@PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
public class TransaccionController {

    private final ITransaccionService tS;
    private final IUserService uS;
    private final IPublicationRepository publicationRepository;

    public TransaccionController(
            ITransaccionService tS,
            IUserService uS,
            IPublicationRepository publicationRepository) {

        this.tS = tS;
        this.uS = uS;
        this.publicationRepository = publicationRepository;
    }

    // LISTAR TODAS: SOLO ADMINISTRADOR
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<TransaccionDTO>> listar() {

        List<TransaccionDTO> lista = tS.list()
                .stream()
                .map(this::convertirDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // LISTAR POR USUARIO
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TransaccionUsuarioDTO>> listarPorUsuario(
            @PathVariable("userId") Long userId,
            Authentication authentication) {

        User user = buscarUsuario(userId);

        verificarPermiso(user, authentication);

        return ResponseEntity.ok(
                tS.findTransactionsByUserId(userId)
        );
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> buscarId(
            @PathVariable("id") Long id,
            Authentication authentication) {

        Transaccion transaccion = buscarTransaccion(id);

        verificarPermiso(transaccion.getUser(), authentication);

        return ResponseEntity.ok(convertirDTO(transaccion));
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<TransaccionDTO> registrar(
            @Valid @RequestBody TransaccionDTO dto,
            Authentication authentication) {

        User user = buscarUsuario(dto.getIdUser());

        verificarPermiso(user, authentication);

        Publication publication = buscarPublicacion(
                dto.getIdPublication()
        );

        validarTipoOperacion(dto, publication);

        if (!"Solicitada".equals(dto.getStatusTransaction())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Una transacción nueva debe tener estado Solicitada"
            );
        }

        Transaccion transaccion = new Transaccion();

        transaccion.setTypeTransaction(dto.getTypeTransaction());
        transaccion.setQuantityTransaction(dto.getQuantityTransaction());
        transaccion.setAgreedUnitPrice(dto.getAgreedUnitPrice());
        transaccion.setDescriptionTransaction(
                dto.getDescriptionTransaction()
        );
        transaccion.setDateRegisterTransaction(LocalDateTime.now());
        transaccion.setStatusTransaction("Solicitada");
        transaccion.setPublication(publication);
        transaccion.setUser(user);

        tS.insert(transaccion);

        // Consultar el registro guardado, incluido el monto calculado.
        Transaccion guardada = buscarTransaccion(
                transaccion.getIdTransaccion()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(guardada.getIdTransaccion())
                .toUri();

        return ResponseEntity.created(location)
                .body(convertirDTO(guardada));
    }

    // ACTUALIZAR
    @PutMapping
    public ResponseEntity<TransaccionDTO> actualizar(
            @Valid @RequestBody TransaccionDTO dto,
            Authentication authentication) {

        if (dto.getIdTransaccion() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la transacción es obligatorio para actualizar"
            );
        }

        Transaccion transaccion = buscarTransaccion(
                dto.getIdTransaccion()
        );

        verificarPermiso(transaccion.getUser(), authentication);

        if (!Objects.equals(
                transaccion.getUser().getIdUser(),
                dto.getIdUser())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el usuario adquirente"
            );
        }

        if (!Objects.equals(
                transaccion.getPublication().getId(),
                dto.getIdPublication())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar la publicación de la transacción"
            );
        }

        validarTipoOperacion(dto, transaccion.getPublication());

        transaccion.setTypeTransaction(dto.getTypeTransaction());
        transaccion.setQuantityTransaction(dto.getQuantityTransaction());
        transaccion.setAgreedUnitPrice(dto.getAgreedUnitPrice());
        transaccion.setDescriptionTransaction(
                dto.getDescriptionTransaction()
        );

        actualizarFechas(transaccion, dto.getStatusTransaction());

        transaccion.setStatusTransaction(dto.getStatusTransaction());

        tS.update(transaccion);

        // Obtener el monto recalculado después de guardar.
        Transaccion actualizada = buscarTransaccion(
                transaccion.getIdTransaccion()
        );

        return ResponseEntity.ok(convertirDTO(actualizada));
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable("id") Long id,
            Authentication authentication) {

        Transaccion transaccion = buscarTransaccion(id);

        verificarPermiso(transaccion.getUser(), authentication);

        tS.delete(id);

        return ResponseEntity.ok(
                "Transacción eliminada correctamente"
        );
    }

    // MÉTODOS AUXILIARES
    private Transaccion buscarTransaccion(Long id) {
        return tS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la transacción con ID: " + id
                ));
    }

    private User buscarUsuario(Long id) {
        return uS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con ID: " + id
                ));
    }

    private Publication buscarPublicacion(Long id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la publicación con ID: " + id
                ));
    }

    private void verificarPermiso(
            User user,
            Authentication authentication) {

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        "Administrador".equals(a.getAuthority())
                );

        boolean esPropietario = user.getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para gestionar "
                            + "las transacciones de otro usuario"
            );
        }
    }

    private void validarTipoOperacion(
            TransaccionDTO dto,
            Publication publication) {

        if (!Objects.equals(
                publication.getOperationType(),
                dto.getTypeTransaction())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El tipo de operación debe coincidir "
                            + "con el de la publicación"
            );
        }
    }

    private void actualizarFechas(
            Transaccion transaccion,
            String nuevoEstado) {

        if (Objects.equals(
                transaccion.getStatusTransaction(),
                nuevoEstado)) {
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();

        if ("Solicitada".equals(nuevoEstado)) {
            transaccion.setReservationDate(null);
            transaccion.setClosingDate(null);
        } else if ("Reservada".equals(nuevoEstado)) {
            transaccion.setReservationDate(ahora);
            transaccion.setClosingDate(null);
        } else {
            // Completada, Cancelada o Rechazada.
            transaccion.setClosingDate(ahora);
        }
    }

    private TransaccionDTO convertirDTO(Transaccion transaccion) {

        TransaccionDTO dto = new TransaccionDTO();

        dto.setIdTransaccion(transaccion.getIdTransaccion());
        dto.setTypeTransaction(transaccion.getTypeTransaction());
        dto.setQuantityTransaction(
                transaccion.getQuantityTransaction()
        );
        dto.setAgreedUnitPrice(transaccion.getAgreedUnitPrice());
        dto.setAmountTransaction(transaccion.getAmountTransaction());
        dto.setDescriptionTransaction(
                transaccion.getDescriptionTransaction()
        );
        dto.setDateRegisterTransaction(
                transaccion.getDateRegisterTransaction()
        );
        dto.setReservationDate(transaccion.getReservationDate());
        dto.setClosingDate(transaccion.getClosingDate());
        dto.setStatusTransaction(transaccion.getStatusTransaction());
        dto.setIdPublication(transaccion.getPublication().getId());
        dto.setIdUser(transaccion.getUser().getIdUser());

        return dto;
    }
}