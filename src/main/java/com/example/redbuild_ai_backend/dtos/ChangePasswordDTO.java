package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotBlank(message = "La contraseña actual es obligatoria")
    private String currentPassword;
    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(
            min = 8,
            max = 72,
            message = "La nueva contraseña debe tener entre 8 y 72 caracteres"
    )
    private String newPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
