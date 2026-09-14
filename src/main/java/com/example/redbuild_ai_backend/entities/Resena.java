package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(name = "titleResena", length = 100, nullable = false)
    private String titleResena;

    @Column(name = "descriptionResena", length = 500, nullable = false)
    private String descriptionResena;

    @Column(name = "scoreResena", nullable = false)
    private Integer scoreResena;

    @Column(name = "dateRegisterResena", nullable = false)
    private LocalDateTime dateRegisterResena;

    @Column(name = "statusResena", nullable = false)
    private boolean statusResena;

    public Resena() {
    }

    public Resena(Long idResena, String titleResena, String descriptionResena, Integer scoreResena, LocalDateTime dateRegisterResena, boolean statusResena) {
        this.idResena = idResena;
        this.titleResena = titleResena;
        this.descriptionResena = descriptionResena;
        this.scoreResena = scoreResena;
        this.dateRegisterResena = dateRegisterResena;
        this.statusResena = statusResena;
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
}
