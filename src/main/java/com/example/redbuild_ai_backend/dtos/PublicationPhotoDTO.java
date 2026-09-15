package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PublicationPhotoDTO {
    private Long idPhoto;

    @NotBlank(message = "La URL de la foto es obligatoria")
    private String urlPhoto;

    private String descriptionPhoto;

    @NotNull(message = "La fecha de subida es obligatoria")
    private LocalDateTime uploadDate;

    @NotNull(message = "El ID de la publicacion es obligatorio")
    private Long idPublication;

    public PublicationPhotoDTO() {
    }

    public PublicationPhotoDTO(Long idPhoto, String urlPhoto, String descriptionPhoto, LocalDateTime uploadDate, Long idPublication) {
        this.idPhoto = idPhoto;
        this.urlPhoto = urlPhoto;
        this.descriptionPhoto = descriptionPhoto;
        this.uploadDate = uploadDate;
        this.idPublication = idPublication;
    }

    public Long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getDescriptionPhoto() {
        return descriptionPhoto;
    }

    public void setDescriptionPhoto(String descriptionPhoto) {
        this.descriptionPhoto = descriptionPhoto;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Long idPublication) {
        this.idPublication = idPublication;
    }
}
