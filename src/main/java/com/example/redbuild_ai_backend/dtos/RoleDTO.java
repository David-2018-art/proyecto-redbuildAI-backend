package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.*;

public class RoleDTO {
    private Long idRole;

    @NotBlank(message = "nameRole es obligatorio")
    @Size(max = 100)
    private String nameRole;

    @NotBlank(message = "descriptionRole es obligatorio")
    @Size(max = 150)
    private String descriptionRole;

    @NotBlank(message = "statusRole es obligatorio")
    @Size(max = 20)
    private String statusRole;

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
