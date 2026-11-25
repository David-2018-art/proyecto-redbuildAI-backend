package com.example.redbuild_ai_backend.dtos;

public class TransaccionUsuarioDTO extends TransaccionDTO {

    private String nameUser;
    private String emailUser;

    public TransaccionUsuarioDTO() {
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
}