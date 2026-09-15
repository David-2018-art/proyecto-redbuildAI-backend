package com.example.redbuild_ai_backend.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductDTO {

    private Long IdProduct;
    @NotBlank(message = "el nombre del producto es obligatorio.")
    private String nameProduct;
    @NotBlank(message = "la descripcion del producto es obligatorio.")
    private String descriptionProduct;
    @NotBlank(message = "el nombre del material es obligatorio.")
    private String material;
    @NotBlank(message = "el color del material es obligatorio.")
    private String colour;
    @NotBlank (message = "el estado del material es obligatorio.")
    private String statusMaterial;
    @NotNull(message = "la cantidad disponible es obligatorio.")
    private BigDecimal quantityProduct;
    @NotBlank(message = "la unidad de medida es obligatorio.")
    private String unidadMedidaProduct;
    @NotNull(message = "el precio del producto es obligatorio.")
    private BigDecimal priceProduct;
    private LocalDate dateRegisterProduct;
    @NotBlank(message = "el estado del producto es obligatorio.")
    private String statusProduct;
    @NotNull(message = "el id de la categoria es obligatoria.")
    private Long idCategory;
    @NotNull(message = "El propietario es obligatorio")
    private Long idUser;

    public ProductDTO() {
    }

    public Long getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(Long idProduct) {
        IdProduct = idProduct;
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

    public LocalDate getDateRegisterProduct() {
        return dateRegisterProduct;
    }

    public void setDateRegisterProduct(LocalDate dateRegisterProduct) {
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
