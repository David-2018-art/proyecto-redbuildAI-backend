package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.ResenaDTO;
import com.example.redbuild_ai_backend.entities.Resena;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IResenaService;
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
@RequestMapping("/api/Resenas")
public class ResenaController {

    private final IResenaService rS;
    private final IUserService uS;
    private final ModelMapper modelMapper;

    public ResenaController(IResenaService rS, IUserService uS, ModelMapper modelMapper) {
        this.rS = rS;
        this.uS = uS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
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

    @PostMapping
    public ResponseEntity<ResenaDTO> registrar(@Valid @RequestBody ResenaDTO dto) {
        Resena resena = modelMapper.map(dto, Resena.class);
        resena.setIdResena(null);
        resena.setDateRegisterResena(LocalDateTime.now());

        // Asociar usuario (se requiere dto.idUser)
        User user = uS.listId(dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con ID: " + dto.getIdUser()));
        resena.setUser(user);

        rS.insert(resena);


        ResenaDTO responseDTO = modelMapper.map(resena, ResenaDTO.class);
        responseDTO.setIdUser(resena.getUser().getIdUser());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resena.getIdResena())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> buscarId(@PathVariable Long id) {
        Resena resena = rS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la reseña con ID: " + id));

        ResenaDTO dto = modelMapper.map(resena, ResenaDTO.class);
        dto.setIdUser(resena.getUser().getIdUser());
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ResenaDTO> actualizar(@Valid @RequestBody ResenaDTO dto) {
        if (dto.getIdResena() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la reseña es obligatorio para actualizar");
        }

        Optional<Resena> existente = rS.listId(dto.getIdResena());
        if (existente.isEmpty()) {
            throw new ResourceNotFoundException("No existe la reseña con ID: " + dto.getIdResena());
        }

        Resena resena = existente.get();
        // No permitir cambiar propietario (idUser)
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Resena resena = rS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la reseña con ID: " + id));

        rS.delete(resena.getIdResena());
        return ResponseEntity.ok("Reseña eliminada correctamente");
    }
}
