package com.example.redbuild_ai_backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(
        name = "ResenaDTO",
        description = "Representa la información de una reseña creada por un usuario.",
        example = "{\"titleResena\":\"Muy buena atención\",\"descriptionResena\":\"El servicio fue rápido y la atención fue excelente.\",\"scoreResena\":5,\"statusResena\":true,\"idUser\":7}"
)
public class ResenaDTO {

    @Schema(description = "ID único de la reseña.", example = "1")
    private Long idResena;

    @Schema(description = "Título o resumen corto de la reseña.", example = "Muy buena atención")
    @NotBlank(message = "el titulo de la reseña es obligatorio.")
    private String titleResena;

    @Schema(description = "Descripción detallada de la experiencia del usuario.", example = "El servicio fue rápido y la atención fue excelente.")
    @NotBlank(message = "la descripcion de la reseña es obligatoria.")
    private String descriptionResena;

    @Schema(description = "Puntaje de la reseña, de 1 a 5.", example = "5")
    @NotNull(message = "la calificación es obligatoria.")
    private Integer scoreResena;

    @Schema(description = "Fecha y hora en que se registró la reseña.", example = "2026-09-23T20:30:00")
    private LocalDateTime dateRegisterResena;

    @Schema(description = "Estado de la reseña: activa o inactiva.", example = "true")
    @NotNull(message = "el estado de la reseña es obligatorio.")
    private boolean statusResena;

    @Schema(description = "ID del usuario que creó la reseña.", example = "7")
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

