package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FotosPublicacion")
public class PublicationPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhoto;

    @Column(name = "urlPhoto",length = 500, nullable = false)
    private String urlPhoto;

    @Column(name = "descriptionPhoto",length = 500)
    private String descriptionPhoto;

    @Column(name = "uploadDate", nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    public PublicationPhoto() {
    }

    public PublicationPhoto(Long idPhoto, String urlPhoto, String descriptionPhoto, LocalDateTime uploadDate, Publication publication) {
        this.idPhoto = idPhoto;
        this.urlPhoto = urlPhoto;
        this.descriptionPhoto = descriptionPhoto;
        this.uploadDate = uploadDate;
        this.publication = publication;
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

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
