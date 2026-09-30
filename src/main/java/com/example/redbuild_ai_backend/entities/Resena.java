package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "Resenas",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_resena_transaccion_autor",
                        columnNames = {"id_transaccion", "id_user"}
                )
        }
)
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(name = "titleResena", length = 100, nullable = false)
    private String titleResena;

    @Column(name = "descriptionResena", length = 500)
    private String descriptionResena;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "scoreResena", nullable = false)
    private Integer scoreResena;

    @Column(
            name = "dateRegisterResena",
            nullable = false,
            updatable = false
    )
    private LocalDateTime dateRegisterResena;

    @Column(name = "statusResena", nullable = false)
    private boolean statusResena = true;

    // Usuario que escribe la reseña.
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    // Usuario que recibe la calificación.
    @ManyToOne
    @JoinColumn(name = "id_usuario_calificado", nullable = false)
    private User ratedUser;

    // Transacción sobre la que se escribe la reseña.
    @ManyToOne
    @JoinColumn(name = "id_transaccion", nullable = false)
    private Transaccion transaccion;

    public Resena() {
    }

    public Resena(
            Long idResena,
            String titleResena,
            String descriptionResena,
            Integer scoreResena,
            LocalDateTime dateRegisterResena,
            boolean statusResena,
            User user,
            User ratedUser,
            Transaccion transaccion) {

        this.idResena = idResena;
        this.titleResena = titleResena;
        this.descriptionResena = descriptionResena;
        this.scoreResena = scoreResena;
        this.dateRegisterResena = dateRegisterResena;
        this.statusResena = statusResena;
        this.user = user;
        this.ratedUser = ratedUser;
        this.transaccion = transaccion;
    }

    @PrePersist
    public void asignarFecha() {
        this.dateRegisterResena = LocalDateTime.now();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }
}