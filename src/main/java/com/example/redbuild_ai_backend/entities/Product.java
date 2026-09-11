package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Productos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdProduct;

    @Column(name = "nameProduct",length = 100,nullable = false)
    private String nameProduct;

    @Column(name = "descriptionProduct",length = 500)
    private String descriptionProduct;

    @Column(name = "material",length = 80)
    private String material;

    @Column(name = "colour",length = 40)
    private String colour;

    @Column(name = "statusMaterial",length = 30,nullable = false)
    private boolean statusMaterial;

    @Column(name = "quantityProduct",nullable = false)
    private double quantityProduct;

    @Column(name = "unidadMedidaProduct",length = 30,nullable = false)
    private String unidadMedidaProduct;

    @Column(name = "priceProduct", nullable = false)
    private double priceProduct;

    @Column(name = "dateRegisterProduct", nullable = false)
    private LocalDateTime dateRegisterProduct;

    @Column(name = "statusProduct",length = 20,nullable = false)
    private boolean statusProduct;

    @ManyToOne
    @JoinColumn(name = "IdCategory",nullable = false)
    private Category category;



    public Product() {
    }

    public Product(Long idProduct, String nameProduct, String descriptionProduct, String material, String colour, boolean statusMaterial, double quantityProduct, String unidadMedidaProduct, double priceProduct, LocalDateTime dateRegisterProduct, boolean statusProduct, Category category) {
        IdProduct = idProduct;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.material = material;
        this.colour = colour;
        this.statusMaterial = statusMaterial;
        this.quantityProduct = quantityProduct;
        this.unidadMedidaProduct = unidadMedidaProduct;
        this.priceProduct = priceProduct;
        this.dateRegisterProduct = dateRegisterProduct;
        this.statusProduct = statusProduct;
        this.category = category;
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

    public LocalDateTime getDateRegisterProduct() {
        return dateRegisterProduct;
    }

    public void setDateRegisterProduct(LocalDateTime dateRegisterProduct) {
        this.dateRegisterProduct = dateRegisterProduct;
    }

    public boolean isStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(boolean statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
