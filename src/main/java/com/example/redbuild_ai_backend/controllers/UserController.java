package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.UserDTO;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
import com.example.redbuild_ai_backend.entities.Role;
import com.example.redbuild_ai_backend.serviceinterfaces.IRoleService;
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
@RequestMapping("/api/Users")
public class UserController {
    private final IUserService uS;
    private final IRoleService rS;
    private final ModelMapper modelMapper;

    public UserController(IUserService uS, IRoleService rS, ModelMapper modelMapper) {
        this.uS = uS;
        this.rS = rS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listar(){
        List<UserDTO> lista=uS.list()
                .stream()
                .map(u -> {
                    UserDTO dto = modelMapper.map(u, UserDTO.class);
                    dto.setIdRole(u.getRole().getIdRole());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<UserDTO> registrar(@Valid @RequestBody UserDTO dto){
        Role role = rS.listId(dto.getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el rol con ID: " + dto.getIdRole()));
        User user=modelMapper.map(dto,User.class);
        user.setRole(role);
        user.setIdUser(null);
        uS.insert(user);

        UserDTO responseDTO= modelMapper.map(user,UserDTO.class);
        responseDTO.setIdRole(user.getRole().getIdRole());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getIdUser())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarId(@PathVariable Long id){
        User user = uS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el usuario con ID: " + id));

        UserDTO dto=modelMapper.map(user, UserDTO.class);
        dto.setIdRole(user.getRole().getIdRole());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/estados")
    public ResponseEntity<List<UserDTO>> buscarPorEstado(@RequestParam String status){
        List<UserDTO> lista = uS.listByStatus(status)
                .stream()
                .map(u -> {
                    UserDTO dto = modelMapper.map(u, UserDTO.class);
                    dto.setIdRole(u.getRole().getIdRole());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(lista);
    }


    @PutMapping
    public ResponseEntity<UserDTO> actualizar(@Valid @RequestBody UserDTO dto){
        if(dto.getIdUser()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El id del usuario es obligatorio para actualizar");

        }

        Optional<User>existente=uS.listId(dto.getIdUser());
        if (existente.isEmpty()){
            throw new ResourceNotFoundException("No existe un usuario con el ID:" + dto.getIdUser());
        }
        Role role = rS.listId(dto.getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el rol con ID: " + dto.getIdRole()));
        User user=existente.get();
        user.setRole(role);

        user.setNameUser(dto.getNameUser());
        user.setEmailUser(dto.getEmailUser());
        user.setStatusUser(dto.getStatusUser());

        uS.update(user);

        UserDTO responseDTO=modelMapper.map(user, UserDTO.class);
        responseDTO.setIdRole(user.getRole().getIdRole());
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        User user=uS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el usuario con ID: " + id));
        uS.delete(user.getIdUser());
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
