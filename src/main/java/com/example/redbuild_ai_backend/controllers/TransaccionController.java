package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.TransaccionDTO;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
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
@RequestMapping("/api/Transacciones")
public class TransaccionController {

    private final ITransaccionService tS;
    private final IUserService uS;
    private final ModelMapper modelMapper;

    public TransaccionController(ITransaccionService tS, IUserService uS, ModelMapper modelMapper) {
        this.tS = tS;
        this.uS = uS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<TransaccionDTO>> listar() {
        List<TransaccionDTO> lista = tS.list()
                .stream()
                .map(t -> {
                    TransaccionDTO dto = modelMapper.map(t, TransaccionDTO.class);
                    dto.setIdUser(t.getUser().getIdUser());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<TransaccionDTO> registrar(
            @Valid @RequestBody TransaccionDTO dto,
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
                    "No puedes registrar transacciones para otro usuario"
            );
        }

        Transaccion transaccion = modelMapper.map(dto, Transaccion.class);
        transaccion.setIdTransaccion(null);
        transaccion.setDateRegisterTransaction(LocalDateTime.now());
        transaccion.setUser(user);

        tS.insert(transaccion);

        TransaccionDTO responseDTO = modelMapper.map(transaccion, TransaccionDTO.class);
        responseDTO.setIdUser(transaccion.getUser().getIdUser());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transaccion.getIdTransaccion())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<TransaccionDTO> buscarId(
            @PathVariable Long id,
            Authentication authentication) {

        Transaccion transaccion = tS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe la transacción con ID: " + id
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = transaccion
                .getUser()
                .getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para consultar esta transacción"
            );
        }

        TransaccionDTO dto = modelMapper.map(transaccion, TransaccionDTO.class);
        dto.setIdUser(transaccion.getUser().getIdUser());

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<TransaccionDTO> actualizar(
            @Valid @RequestBody TransaccionDTO dto,
            Authentication authentication) {

        if (dto.getIdTransaccion() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la transacción es obligatorio para actualizar"
            );
        }

        Transaccion transaccion = tS.listId(dto.getIdTransaccion())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe la transacción con ID: " + dto.getIdTransaccion()
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = transaccion
                .getUser()
                .getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para actualizar esta transacción"
            );
        }

        if (!transaccion.getUser().getIdUser().equals(dto.getIdUser())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el propietario de la transacción"
            );
        }

        transaccion.setTypeTransaction(dto.getTypeTransaction());
        transaccion.setAmountTransaction(dto.getAmountTransaction());
        transaccion.setDescriptionTransaction(dto.getDescriptionTransaction());
        transaccion.setPaymentMethod(dto.getPaymentMethod());
        transaccion.setStatusTransaction(dto.isStatusTransaction());

        tS.update(transaccion);

        TransaccionDTO responseDTO = modelMapper.map(transaccion, TransaccionDTO.class);
        responseDTO.setIdUser(transaccion.getUser().getIdUser());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id,
            Authentication authentication) {

        Transaccion transaccion = tS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe la transacción con ID: " + id
                        )
                );

        boolean esAdministrador = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = transaccion
                .getUser()
                .getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para eliminar esta transacción"
            );
        }

        tS.delete(transaccion.getIdTransaccion());

        return ResponseEntity.ok("Transacción eliminada correctamente");
    }
}
