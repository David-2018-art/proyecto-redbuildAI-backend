package com.example.redbuild_ai_backend.dtos;


import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ProductDTO {

    @JsonProperty("idProduct")
    private Long idProduct;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres")
    private String nameProduct;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción admite hasta 500 caracteres")
    private String descriptionProduct;

    @NotBlank(message = "El material es obligatorio")
    @Size(max = 80, message = "El material admite hasta 80 caracteres")
    private String material;

    @NotBlank(message = "El color es obligatorio")
    @Size(max = 40, message = "El color admite hasta 40 caracteres")
    private String colour;

    @NotBlank(message = "El estado del material es obligatorio")
    @Pattern(
            regexp = "Nuevo|Como nuevo|Bueno|Regular",
            message = "El estado del material debe ser Nuevo, Como nuevo, Bueno o Regular"
    )
    private String statusMaterial;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0", message = "La cantidad no puede ser negativa")
    @Digits(
            integer = 9,
            fraction = 3,
            message = "La cantidad admite hasta 9 enteros y 3 decimales"
    )
    private BigDecimal quantityProduct;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 30, message = "La unidad de medida admite hasta 30 caracteres")
    private String unidadMedidaProduct;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    @Digits(
            integer = 10,
            fraction = 2,
            message = "El precio admite hasta 10 enteros y 2 decimales"
    )
    private BigDecimal priceProduct;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateRegisterProduct;

    @NotBlank(message = "El estado del producto es obligatorio")
    @Pattern(
            regexp = "Activo|Inactivo",
            message = "El estado del producto debe ser Activo o Inactivo"
    )
    private String statusProduct;

    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El ID de la categoría debe ser mayor que cero")
    private Long idCategory;

    @NotNull(message = "El propietario es obligatorio")
    @Positive(message = "El ID del propietario debe ser mayor que cero")
    private Long idUser;

    public ProductDTO() {
    }


    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getStatusMaterial() {
        return statusMaterial;
    }

    public void setStatusMaterial(String statusMaterial) {
        this.statusMaterial = statusMaterial;
    }

    public BigDecimal getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(BigDecimal quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public String getUnidadMedidaProduct() {
        return unidadMedidaProduct;
    }

    public void setUnidadMedidaProduct(String unidadMedidaProduct) {
        this.unidadMedidaProduct = unidadMedidaProduct;
    }

    public BigDecimal getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(BigDecimal priceProduct) {
        this.priceProduct = priceProduct;
    }

    public LocalDateTime getDateRegisterProduct() {
        return dateRegisterProduct;
    }

    public void setDateRegisterProduct(LocalDateTime dateRegisterProduct) {
        this.dateRegisterProduct = dateRegisterProduct;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
