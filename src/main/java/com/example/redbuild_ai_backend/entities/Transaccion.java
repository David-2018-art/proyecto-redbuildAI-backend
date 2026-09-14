package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    @Column(name = "typeTransaction", length = 50, nullable = false)
    private String typeTransaction;

    @Column(name = "amountTransaction", nullable = false)
    private double amountTransaction;

    @Column(name = "descriptionTransaction", length = 300, nullable = false)
    private String descriptionTransaction;

    @Column(name = "paymentMethod", length = 60, nullable = false)
    private String paymentMethod;

    @Column(name = "dateRegisterTransaction", nullable = false)
    private LocalDateTime dateRegisterTransaction;

    @Column(name = "statusTransaction", nullable = false)
    private boolean statusTransaction;

    public Transaccion() {
    }

    public Transaccion(Long idTransaccion, String typeTransaction, double amountTransaction, String descriptionTransaction, String paymentMethod, LocalDateTime dateRegisterTransaction, boolean statusTransaction) {
        this.idTransaccion = idTransaccion;
        this.typeTransaction = typeTransaction;
        this.amountTransaction = amountTransaction;
        this.descriptionTransaction = descriptionTransaction;
        this.paymentMethod = paymentMethod;
        this.dateRegisterTransaction = dateRegisterTransaction;
        this.statusTransaction = statusTransaction;
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

    public double getAmountTransaction() {
        return amountTransaction;
    }

    public void setAmountTransaction(double amountTransaction) {
        this.amountTransaction = amountTransaction;
    }

    public String getDescriptionTransaction() {
        return descriptionTransaction;
    }

    public void setDescriptionTransaction(String descriptionTransaction) {
        this.descriptionTransaction = descriptionTransaction;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getDateRegisterTransaction() {
        return dateRegisterTransaction;
    }

    public void setDateRegisterTransaction(LocalDateTime dateRegisterTransaction) {
        this.dateRegisterTransaction = dateRegisterTransaction;
    }

    public boolean isStatusTransaction() {
        return statusTransaction;
    }

    public void setStatusTransaction(boolean statusTransaction) {
        this.statusTransaction = statusTransaction;
    }
}
