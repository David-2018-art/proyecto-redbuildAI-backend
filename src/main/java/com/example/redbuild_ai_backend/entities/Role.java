package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(name = "nameRole", nullable = false, length = 100)
    private String nameRole;

    @Column(name = "descriptionRole", nullable = false, length = 150)
    private String descriptionRole;

    @Column(name = "statusRole", nullable = false, length = 20)
    private String statusRole;

    public Role() {
    }

    public Role(Long idRole, String nameRole, String descriptionRole, String statusRole) {
        this.idRole = idRole;
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
        this.statusRole = statusRole;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public String getDescriptionRole() {
        return descriptionRole;
    }

    public void setDescriptionRole(String descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    public String getStatusRole() {
        return statusRole;
    }

    public void setStatusRole(String statusRole) {
        this.statusRole = statusRole;
    }

}
