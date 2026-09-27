package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.*;

public class UserDTO {
    private Long idUser;

    @NotBlank(message = "nameUser es obligatorio")
    @Size(max = 80)
    private String nameUser;

    @Size(max = 80)
    private String lastNameUser;

    @NotBlank(message = "emailUser es obligatorio")
    @Size(max = 254)
    @Email(message = "El correo debe ser válido")
    private String emailUser;


    @Size(max = 20)
    private String phoneUser;

    @Size(max = 100)
    private String companyNameUser;

    @NotBlank(message = "statusUser es obligatorio")
    @Size(max = 20)
    private String statusUser;

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El ID del rol debe ser positivo")
    private Long idRole;

    public UserDTO() {
    }


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

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getCompanyNameUser() {
        return companyNameUser;
    }

    public void setCompanyNameUser(String companyNameUser) {
        this.companyNameUser = companyNameUser;
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
