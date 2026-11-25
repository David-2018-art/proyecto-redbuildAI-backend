package com.example.redbuild_ai_backend.dtos;

public class LoginResponseDTO {
    private String token;
    private Long idUser;
    private String nameUser;
    private String emailUser;
    private String role;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Long idUser, String nameUser, String emailUser, String role) {
        this.token = token;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
