package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.*;

public class UserDTO {
    private Long idUser;

    @NotBlank(message = "nameUser es obligatorio")
    @Size(max = 100)
    private String nameUser;

    @NotBlank(message = "emailUser es obligatorio")
    @Size(max = 150)
    @Email(message = "El correo debe ser valido")
    private String emailUser;

    @NotBlank(message = "statusUser es obligatorio")
    @Size(max = 20)
    private String statusUser;

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El ID del rol debe ser positivo")
    private Long idRole;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

}
