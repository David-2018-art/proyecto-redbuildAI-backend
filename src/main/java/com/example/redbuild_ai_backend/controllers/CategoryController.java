package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.CategoryDTO;
import com.example.redbuild_ai_backend.entities.Category;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.ICategoryService;
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
@RequestMapping("/api/Categories")
public class CategoryController {
    private final ICategoryService cS;
    private final ModelMapper modelMapper;

    public CategoryController(ICategoryService cS, ModelMapper modelMapper) {
        this.cS = cS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listar(){
        List<CategoryDTO> lista=cS.list()
                .stream()
                .map(c-> modelMapper.map(c,CategoryDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> registrar(@Valid @RequestBody CategoryDTO dto){
        Category category=modelMapper.map(dto,Category.class);
        category.setIdCategory(null);
        cS.insert(category);

        CategoryDTO responseDTO= modelMapper.map(category,CategoryDTO.class);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getIdCategory())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> buscarId(@PathVariable Long id){
        Category category = cS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra la categoria con ID: " + id));

        CategoryDTO dto=modelMapper.map(category, CategoryDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> actualizar(@Valid @RequestBody CategoryDTO dto){
        if(dto.getIdCategory()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El id de la categoria es obligatoria para actualizar");

        }

        Optional<Category>existente=cS.listId(dto.getIdCategory());
        if (existente.isEmpty()){
            throw new ResourceNotFoundException("No existe una categoria con el ID:" + dto.getIdCategory());
        }
        Category category=existente.get();

        category.setNameCategory(dto.getNameCategory());
        category.setDescriptionCategory(dto.getDescriptionCategory());
        category.setStatusCategory(dto.getStatusCategory());

        cS.update(category);

        CategoryDTO responseDTO=modelMapper.map(category, CategoryDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        Category category=cS.listId(id)
                .orElseThrow(()->new ResourceNotFoundException("No se encuentra la categoria con ID: " + id));
        cS.delete(category.getIdCategory());
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }
}
