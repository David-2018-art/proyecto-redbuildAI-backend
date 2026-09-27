package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.LocationDTO;
import com.example.redbuild_ai_backend.entities.Location;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.ILocationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Locations")
public class LocationController {
    private final ILocationService lS;
    private final ModelMapper modelMapper;

    public LocationController(ILocationService lS, ModelMapper modelMapper) {
        this.lS = lS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<LocationDTO>> listar(){
        List<LocationDTO> lista=lS.list()
                .stream()
                .map(l -> modelMapper.map(l, LocationDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<LocationDTO> registrar(@Valid @RequestBody LocationDTO dto){
        Location location = modelMapper.map(dto, Location.class);
        location.setIdLocation(null);

        lS.insert(location);

        LocationDTO responseDTO = modelMapper.map(location, LocationDTO.class);

        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(location.getIdLocation())
                .toUri();

        return ResponseEntity
                .created(locationUri)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<LocationDTO> buscarId(@PathVariable Long id){
        Location location = lS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encuentra la ubicacion con ID: " + id
                        )
                );
        LocationDTO dto = modelMapper.map(location, LocationDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<LocationDTO> actualizar(@Valid @RequestBody LocationDTO dto){
        if(dto.getIdLocation()==null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El id de la ubicacion es obligatorio para actualizar"
            );
        }

        Optional<Location> existente=lS.listId(dto.getIdLocation());

        if (existente.isEmpty()){
            throw new ResourceNotFoundException(
                    "No existe una ubicacion con el ID: " + dto.getIdLocation()
            );
        }
        Location location=existente.get();

        location.setDepartment(dto.getDepartment());
        location.setProvince(dto.getProvince());
        location.setDistrict(dto.getDistrict());
        location.setReferenceAddress(dto.getReferenceAddress());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());

        lS.update(location);

        LocationDTO responseDTO=modelMapper.map(location, LocationDTO.class);
        return ResponseEntity.ok(responseDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        Location location = lS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No se encuentra la ubicacion con ID: " + id
                        )
                );
        lS.delete(location.getIdLocation());
        return ResponseEntity.ok("Ubicacion eliminada correctamente");
    }

    @GetMapping("/department/{department}")
    @PreAuthorize("hasAnyAuthority('Usuario','Empresa','Administrador')")
    public ResponseEntity<List<LocationDTO>> buscarPorDepartamento(
            @PathVariable String department) {
        List<LocationDTO> lista = lS.findByDepartment(department)
                .stream()
                .map(l -> modelMapper.map(l, LocationDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }
}
