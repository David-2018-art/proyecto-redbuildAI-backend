package com.example.redbuild_ai_backend.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    private Long idCategory;
    @NotBlank(message = "el nombre de la categoria es obligatorio")
    private String nameCategory;
    @NotBlank(message = "la descripcion de la categoria es obligatorio")
    private String descriptionCategory;
    @NotBlank(message = "el estado es obligatorio")
    private String statusCategory;



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

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }

    public String getStatusCategory() {
        return statusCategory;
    }

    public void setStatusCategory(String statusCategory) {
        this.statusCategory = statusCategory;
    }
}
