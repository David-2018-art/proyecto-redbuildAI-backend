package com.example.redbuild_ai_backend.dtos;

public class CategoryProductCountDTO {
    private Long idCategory;
    private String nameCategory;
    private Long quantityProducts;

    public CategoryProductCountDTO() {
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Long getQuantityProducts() {
        return quantityProducts;
    }

    public void setQuantityProducts(Long quantityProducts) {
        this.quantityProducts = quantityProducts;
    }
}
