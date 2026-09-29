package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    @Column(name = "typeTransaction", length = 20, nullable = false)
    private String typeTransaction;

    @Column(
            name = "quantityTransaction",
            precision = 12,
            scale = 3,
            nullable = false
    )
    private BigDecimal quantityTransaction;

    @Column(
            name = "agreedUnitPrice",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private BigDecimal agreedUnitPrice;

    @Column(
            name = "amountTransaction",
            precision = 24,
            scale = 2,
            nullable = false
    )
    private BigDecimal amountTransaction;

    @Column(name = "descriptionTransaction", length = 500)
    private String descriptionTransaction;

    @Column(name = "dateRegisterTransaction", nullable = false)
    private LocalDateTime dateRegisterTransaction;

    @Column(name = "reservationDate")
    private LocalDateTime reservationDate;

    @Column(name = "closingDate")
    private LocalDateTime closingDate;

    @Column(name = "statusTransaction", length = 20, nullable = false)
    private String statusTransaction;

    @ManyToOne
    @JoinColumn(name = "id_publication", nullable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Transaccion() {
    }

    public Transaccion(
            Long idTransaccion,
            String typeTransaction,
            BigDecimal quantityTransaction,
            BigDecimal agreedUnitPrice,
            String descriptionTransaction,
            LocalDateTime dateRegisterTransaction,
            LocalDateTime reservationDate,
            LocalDateTime closingDate,
            String statusTransaction,
            Publication publication,
            User user) {

        this.idTransaccion = idTransaccion;
        this.typeTransaction = typeTransaction;
        this.quantityTransaction = quantityTransaction;
        this.agreedUnitPrice = agreedUnitPrice;
        this.descriptionTransaction = descriptionTransaction;
        this.dateRegisterTransaction = dateRegisterTransaction;
        this.reservationDate = reservationDate;
        this.closingDate = closingDate;
        this.statusTransaction = statusTransaction;
        this.publication = publication;
        this.user = user;
    }

    @PrePersist
    public void prepararRegistro() {
        if (dateRegisterTransaction == null) {
            dateRegisterTransaction = LocalDateTime.now();
        }

        if (statusTransaction == null) {
            statusTransaction = "Solicitada";
        }

        calcularMonto();
    }

    @PreUpdate
    public void prepararActualizacion() {
        calcularMonto();
    }

    private void calcularMonto() {
        if (quantityTransaction != null && agreedUnitPrice != null) {
            amountTransaction = quantityTransaction
                    .multiply(agreedUnitPrice)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public BigDecimal getQuantityTransaction() {
        return quantityTransaction;
    }

    public void setQuantityTransaction(BigDecimal quantityTransaction) {
        this.quantityTransaction = quantityTransaction;
    }

    public BigDecimal getAgreedUnitPrice() {
        return agreedUnitPrice;
    }

    public void setAgreedUnitPrice(BigDecimal agreedUnitPrice) {
        this.agreedUnitPrice = agreedUnitPrice;
    }

    public BigDecimal getAmountTransaction() {
        return amountTransaction;
    }

    public String getDescriptionTransaction() {
        return descriptionTransaction;
    }

    public void setDescriptionTransaction(String descriptionTransaction) {
        this.descriptionTransaction = descriptionTransaction;
    }

    public LocalDateTime getDateRegisterTransaction() {
        return dateRegisterTransaction;
    }

    public void setDateRegisterTransaction(LocalDateTime dateRegisterTransaction) {
        this.dateRegisterTransaction = dateRegisterTransaction;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public String getStatusTransaction() {
        return statusTransaction;
    }

    public void setStatusTransaction(String statusTransaction) {
        this.statusTransaction = statusTransaction;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}