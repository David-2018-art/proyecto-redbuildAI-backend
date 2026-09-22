package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.UserDTO;
import com.example.redbuild_ai_backend.dtos.UserRegisterDTO;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
import com.example.redbuild_ai_backend.entities.Role;
import com.example.redbuild_ai_backend.serviceinterfaces.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @PreAuthorize("hasAuthority('Administrador')")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<UserDTO> buscarId(@PathVariable Long id, Authentication authentication){
        User user = uS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encuentra el usuario con ID: " + id));

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = user.getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para consultar este usuario"
            );
        }

        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setIdRole(user.getRole().getIdRole());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/estados")
    @PreAuthorize("hasAuthority('Administrador')")
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
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<UserDTO> actualizar(@Valid @RequestBody UserDTO dto,
                                              Authentication authentication){
        if (dto.getIdUser() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El id del usuario es obligatorio para actualizar"
            );
        }

        User user = uS.listId(dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con el ID: " + dto.getIdUser()
                ));

        boolean esAdministrador = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        boolean esPropietario = user.getEmailUser()
                .equalsIgnoreCase(authentication.getName());

        if (!esAdministrador && !esPropietario) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para actualizar este usuario"
            );
        }

        // Datos que el propio usuario puede modificar
        user.setNameUser(dto.getNameUser());
        user.setLastNameUser(dto.getLastNameUser());
        user.setEmailUser(dto.getEmailUser());
        user.setPhoneUser(dto.getPhoneUser());
        user.setCompanyNameUser(dto.getCompanyNameUser());

        // Solamente el administrador puede cambiar rol y estado
        if (esAdministrador) {

            Role role = rS.listId(dto.getIdRole())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No existe el rol con ID: " + dto.getIdRole()
                    ));

            user.setRole(role);
            user.setStatusUser(dto.getStatusUser());
        }

        uS.update(user);

        UserDTO responseDTO = modelMapper.map(user, UserDTO.class);
        responseDTO.setIdRole(user.getRole().getIdRole());

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        User user=uS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra el usuario con ID: " + id));
        uS.delete(user.getIdUser());
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @PostMapping
    public ResponseEntity<UserDTO> registrar(
            @Valid @RequestBody UserRegisterDTO dto) {

        // El servidor elige el rol de las cuentas nuevas.
        Role role = rS.buscarPorNombre("Usuario")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "No está configurado el rol Usuario"
                ));

        if (!"Activo".equals(role.getStatusRole())) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "El registro de usuarios no está disponible"
            );
        }

        User user = new User();

        user.setNameUser(dto.getNameUser());
        user.setLastNameUser(dto.getLastNameUser());
        user.setEmailUser(dto.getEmailUser());
        user.setPasswordUser(dto.getPasswordUser());
        user.setPhoneUser(dto.getPhoneUser());
        user.setCompanyNameUser(dto.getCompanyNameUser());


        user.setIdUser(null);
        user.setRole(role);
        user.setStatusUser("Activo");


        uS.insert(user);

        UserDTO responseDTO = modelMapper.map(user, UserDTO.class);
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
}
