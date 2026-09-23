package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ResenaDTO {

    private Long idResena;

    @NotBlank(message = "el titulo de la reseña es obligatorio.")
    private String titleResena;

    @NotBlank(message = "la descripcion de la reseña es obligatoria.")
    private String descriptionResena;

    @NotNull(message = "la calificación es obligatoria.")
    private Integer scoreResena;

    private LocalDateTime dateRegisterResena;

    @NotNull(message = "el estado de la reseña es obligatorio.")
    private boolean statusResena;

    // id del usuario que creó la reseña
    private Long idUser;

    public ResenaDTO() {
    }

    public Long getIdResena() {
        return idResena;
    }

    public void setIdResena(Long idResena) {
        this.idResena = idResena;
    }

    public String getTitleResena() {
        return titleResena;
    }

    public void setTitleResena(String titleResena) {
        this.titleResena = titleResena;
    }

    public String getDescriptionResena() {
        return descriptionResena;
    }

    public void setDescriptionResena(String descriptionResena) {
        this.descriptionResena = descriptionResena;
    }

    public Integer getScoreResena() {
        return scoreResena;
    }

    public void setScoreResena(Integer scoreResena) {
        this.scoreResena = scoreResena;
    }

    public LocalDateTime getDateRegisterResena() {
        return dateRegisterResena;
    }

    public void setDateRegisterResena(LocalDateTime dateRegisterResena) {
        this.dateRegisterResena = dateRegisterResena;
    }

    public boolean isStatusResena() {
        return statusResena;
    }

    public void setStatusResena(boolean statusResena) {
        this.statusResena = statusResena;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}

