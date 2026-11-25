package com.example.redbuild_ai_backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Schema(
        name = "ResenaDTO",
        description = "Calificación de un usuario sobre una transacción"
)
public class ResenaDTO {

    private Long idResena;

    @NotBlank(message = "El título de la reseña es obligatorio")
    @Size(max = 100, message = "El título admite hasta 100 caracteres")
    private String titleResena;

    @Size(
            max = 500,
            message = "El comentario admite hasta 500 caracteres"
    )
    private String descriptionResena;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer scoreResena;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateRegisterResena;

    @NotNull(message = "El estado de la reseña es obligatorio")
    private Boolean statusResena;

    @NotNull(message = "El ID del autor es obligatorio")
    @Positive(message = "El ID del autor debe ser mayor que cero")
    private Long idUser;

    @NotNull(message = "El ID del usuario calificado es obligatorio")
    @Positive(
            message = "El ID del usuario calificado debe ser mayor que cero"
    )
    private Long idRatedUser;

    @NotNull(message = "El ID de la transacción es obligatorio")
    @Positive(
            message = "El ID de la transacción debe ser mayor que cero"
    )
    private Long idTransaccion;

    public ResenaDTO() {
    }

    @AssertTrue(message = "No puedes calificarte a ti mismo")
    @JsonIgnore
    @Schema(hidden = true)
    public boolean isUsuariosDiferentes() {
        if (idUser == null || idRatedUser == null) {
            return true;
        }

        return !Objects.equals(idUser, idRatedUser);
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

    public Boolean getStatusResena() {
        return statusResena;
    }

    public void setStatusResena(Boolean statusResena) {
        this.statusResena = statusResena;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdRatedUser() {
        return idRatedUser;
    }

    public void setIdRatedUser(Long idRatedUser) {
        this.idRatedUser = idRatedUser;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
}