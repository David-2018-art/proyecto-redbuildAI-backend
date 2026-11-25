package com.example.redbuild_ai_backend.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    @Column(name = "nameCategory",nullable = false,length = 60)
    private String nameCategory;
    @Column(name = "descriptionCategory",nullable = false,length = 150)
    private String descriptionCategory;
    @Column(name = "statusCategory",nullable = false,length = 20)
    private String statusCategory;

    public Category() {
    }

    public Category(Long idCategory, String nameCategory, String descriptionCategory, String statusCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.descriptionCategory = descriptionCategory;
        this.statusCategory = statusCategory;
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
