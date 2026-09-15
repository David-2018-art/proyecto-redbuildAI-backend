package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "nameUser", nullable = false, length = 100)
    private String nameUser;

    @Column(name = "emailUser", nullable = false, length = 150)
    private String emailUser;

    @Column(name = "statusUser", nullable = false, length = 20)
    private String statusUser;

    @ManyToOne
    @JoinColumn(name = "IdRole", nullable = false)
    private Role role;

    public User() {
    }

    public User(Long idUser, String nameUser, String emailUser, String statusUser, Role role) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.statusUser = statusUser;
        this.role = role;
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

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
