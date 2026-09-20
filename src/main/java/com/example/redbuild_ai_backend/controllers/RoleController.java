package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.RoleDTO;
import com.example.redbuild_ai_backend.entities.Role;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IRoleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Roles")
public class RoleController {
    private final IRoleService rS;
    private final ModelMapper modelMapper;

    public RoleController(IRoleService rS, ModelMapper modelMapper) {
        this.rS = rS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> listar(){
        List<RoleDTO> lista=rS.list()
                .stream()
                .map(r-> modelMapper.map(r,RoleDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> registrar(@Valid @RequestBody RoleDTO dto){
        Role role=modelMapper.map(dto,Role.class);
        role.setIdRole(null);
        rS.insert(role);

        RoleDTO responseDTO= modelMapper.map(role,RoleDTO.class);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getIdRole())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> buscarId(@PathVariable Long id){
        Role role = rS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el rol con ID: " + id));

        RoleDTO dto=modelMapper.map(role, RoleDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<RoleDTO>> buscarPorEstado(@RequestParam String status){
        List<RoleDTO> lista = rS.listByStatus(status)
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping
    public ResponseEntity<RoleDTO> actualizar(@Valid @RequestBody RoleDTO dto){
        if(dto.getIdRole()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El id del rol es obligatorio para actualizar");

        }

        Optional<Role>existente=rS.listId(dto.getIdRole());
        if (existente.isEmpty()){
            throw new ResourceNotFoundException("No existe un rol con el ID:" + dto.getIdRole());
        }
        Role role=existente.get();

        role.setNameRole(dto.getNameRole());
        role.setDescriptionRole(dto.getDescriptionRole());
        role.setStatusRole(dto.getStatusRole());

        rS.update(role);

        RoleDTO responseDTO=modelMapper.map(role, RoleDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        Role role=rS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el rol con ID: " + id));
        rS.delete(role.getIdRole());
        return ResponseEntity.ok("Rol eliminado correctamente");
    }
}
