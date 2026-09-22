package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.TransaccionDTO;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
import com.example.redbuild_ai_backend.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<TransaccionDTO> registrar(@Valid @RequestBody TransaccionDTO dto) {
        Transaccion transaccion = modelMapper.map(dto, Transaccion.class);
        transaccion.setIdTransaccion(null);
        transaccion.setDateRegisterTransaction(LocalDateTime.now());

        // Asociar usuario (se requiere dto.idUser)
        User user = uS.listId(dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con ID: " + dto.getIdUser()));
        transaccion.setUser(user);

        tS.insert(transaccion);

        TransaccionDTO responseDTO = modelMapper.map(transaccion, TransaccionDTO.class);
        responseDTO.setIdUser(transaccion.getUser().getIdUser());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transaccion.getIdTransaccion())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> buscarId(@PathVariable Long id) {
        Transaccion transaccion = tS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la transacción con ID: " + id));

        TransaccionDTO dto = modelMapper.map(transaccion, TransaccionDTO.class);
        dto.setIdUser(transaccion.getUser().getIdUser());
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<TransaccionDTO> actualizar(@Valid @RequestBody TransaccionDTO dto) {
        if (dto.getIdTransaccion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la transacción es obligatorio para actualizar");
        }

        Optional<Transaccion> existente = tS.listId(dto.getIdTransaccion());
        if (existente.isEmpty()) {
            throw new ResourceNotFoundException("No existe la transacción con ID: " + dto.getIdTransaccion());
        }

        Transaccion transaccion = existente.get();
        // No permitir cambiar propietario (idUser)
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
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Transaccion transaccion = tS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la transacción con ID: " + id));

        tS.delete(transaccion.getIdTransaccion());
        return ResponseEntity.ok("Transacción eliminada correctamente");
    }
}
