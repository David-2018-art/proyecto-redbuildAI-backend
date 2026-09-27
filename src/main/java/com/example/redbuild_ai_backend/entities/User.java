package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "Nombres", nullable = false, length = 80)
    private String nameUser;

    @Column(name = "Apellidos", length = 80)
    private String lastNameUser;

    @Column(name = "Correo", nullable = false, unique = true, length = 254)
    private String emailUser;

    @Column(name = "ContrasenaHash", nullable = false, length = 255)
    private String passwordUser;

    @Column(name = "Telefono", length = 20)
    private String phoneUser;

    @Column(name = "NombreEmpresa", length = 100)
    private String companyNameUser;

    @Column(name = "FechaRegistro", nullable = false)
    private LocalDateTime registrationDateUser;

    @PrePersist
    public void prePersist() {
        if (registrationDateUser == null) {
            registrationDateUser = LocalDateTime.now();
        }
    }

    @Column(name = "Estado", nullable = false, length = 20)
    private String statusUser;

    @ManyToOne
    @JoinColumn(name = "IdRol", nullable = false)
    private Role role;

    public User() {
    }

    public User(Long idUser, String nameUser, String lastNameUser, String emailUser, String passwordUser, String phoneUser, String companyNameUser, LocalDateTime registrationDateUser, String statusUser, Role role) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.lastNameUser = lastNameUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.phoneUser = phoneUser;
        this.companyNameUser = companyNameUser;
        this.registrationDateUser = registrationDateUser;
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

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getCompanyNameUser() {
        return companyNameUser;
    }

    public void setCompanyNameUser(String companyNameUser) {
        this.companyNameUser = companyNameUser;
    }

    public LocalDateTime getRegistrationDateUser() {
        return registrationDateUser;
    }

    public void setRegistrationDateUser(LocalDateTime registrationDateUser) {
        this.registrationDateUser = registrationDateUser;
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
