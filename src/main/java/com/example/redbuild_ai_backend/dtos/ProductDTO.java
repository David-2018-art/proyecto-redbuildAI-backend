package com.example.redbuild_ai_backend.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @NotNull (message = "el estado del material es obligatorio.")
    private boolean statusMaterial;
    @NotNull(message = "la cantidad disponible es obligatorio.")
    private double quantityProduct;
    @NotBlank(message = "la unidad de medida es obligatorio.")
    private String unidadMedidaProduct;
    @NotNull(message = "el precio del producto es obligatorio.")
    private double priceProduct;
    private LocalDate dateRegisterProduct;
    @NotNull(message = "el estado del producto es obligatorio.")
    private boolean statusProduct;
    @NotNull(message = "el id de la categoria es obligatoria.")
    private Long idCategory;


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

    public boolean isStatusMaterial() {
        return statusMaterial;
    }

    public void setStatusMaterial(boolean statusMaterial) {
        this.statusMaterial = statusMaterial;
    }

    public double getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(double quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public String getUnidadMedidaProduct() {
        return unidadMedidaProduct;
    }

    public void setUnidadMedidaProduct(String unidadMedidaProduct) {
        this.unidadMedidaProduct = unidadMedidaProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public LocalDate getDateRegisterProduct() {
        return dateRegisterProduct;
    }

    public void setDateRegisterProduct(LocalDate dateRegisterProduct) {
        this.dateRegisterProduct = dateRegisterProduct;
    }

    public boolean isStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(boolean statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

}
