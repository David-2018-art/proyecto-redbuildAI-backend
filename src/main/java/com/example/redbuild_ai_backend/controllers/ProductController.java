package com.example.redbuild_ai_backend.controllers;


import com.example.redbuild_ai_backend.dtos.ProductDTO;
import com.example.redbuild_ai_backend.entities.Category;
import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.ICategoryService;
import com.example.redbuild_ai_backend.serviceinterfaces.IProductService;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/Products")
public class ProductController {
    private final IProductService pS;
    private final ICategoryService cS;
    private final IUserService uS;
    private final ModelMapper modelMapper;

    public ProductController(IProductService pS, ICategoryService cS, IUserService uS, ModelMapper modelMapper) {
        this.pS = pS;
        this.cS = cS;
        this.uS = uS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>>listar(){
        List<ProductDTO> lista = pS.list()
                .stream()
                .map(product -> {
                    ProductDTO dto =
                            modelMapper.map(product, ProductDTO.class);

                    dto.setIdCategory(
                            product.getCategory().getIdCategory()
                    );

                    dto.setIdUser(
                            product.getUser().getIdUser()
                    );

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(lista);

    }

    @PostMapping
    public ResponseEntity<ProductDTO>registrar(@Valid @RequestBody ProductDTO dto){

        Category category=cS.listId(dto.getIdCategory())
                .orElseThrow(()->new ResourceNotFoundException("No existe la categoria con ID: " + dto.getIdCategory()));

        User user = uS.listId(dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con ID: " + dto.getIdUser()
                ));

                Product product=modelMapper.map(dto, Product.class);

                product.setIdProduct(null);
                product.setDateRegisterProduct(LocalDateTime.now());
                product.setCategory(category);
                product.setUser(user);


                pS.insert(product);
        ProductDTO responseDTO =
                modelMapper.map(product, ProductDTO.class);

        responseDTO.setIdCategory(
                product.getCategory().getIdCategory()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getIdProduct())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> buscarId(
            @PathVariable("id") Long id) {

        Product product = pS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID: " + id
                ));

        ProductDTO dto =
                modelMapper.map(product, ProductDTO.class);

        dto.setIdCategory(
                product.getCategory().getIdCategory()
        );

        dto.setIdUser(
                product.getUser().getIdUser()
        );

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ProductDTO>actualizar(@Valid @RequestBody ProductDTO dto){
        if (dto.getIdProduct() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID del producto es obligatorio para actualizar"
            );
        }

        Product product = pS.listId(dto.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID: " + dto.getIdProduct()
                ));

        Category category = cS.listId(dto.getIdCategory())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la categoria con ID: " + dto.getIdCategory()
                ));

        if (!product.getUser().getIdUser().equals(dto.getIdUser())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el propietario del producto"
            );
        }

        product.setNameProduct(dto.getNameProduct());
        product.setDescriptionProduct(dto.getDescriptionProduct());
        product.setMaterial(dto.getMaterial());
        product.setColour(dto.getColour());
        product.setStatusMaterial(dto.getStatusMaterial());
        product.setQuantityProduct(dto.getQuantityProduct());
        product.setUnidadMedidaProduct(dto.getUnidadMedidaProduct());
        product.setPriceProduct(dto.getPriceProduct());
        product.setStatusProduct(dto.getStatusProduct());
        product.setCategory(category);

        pS.update(product);

        ProductDTO responseDTO =
                modelMapper.map(product, ProductDTO.class);

        responseDTO.setIdCategory(
                product.getCategory().getIdCategory()
        );

        responseDTO.setIdUser(
                product.getUser().getIdUser()
        );

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable Long id){
        Product product = pS.listId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID: " + id
                ));

        pS.delete(product.getIdProduct());

        return ResponseEntity.ok(
                "Producto eliminado correctamente"
        );
    }



}
