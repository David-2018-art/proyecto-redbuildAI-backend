package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TransaccionDTO {

    private Long idTransaccion;

    @NotBlank(message = "el tipo de transacción es obligatorio.")
    private String typeTransaction;

    @NotNull(message = "el monto de la transacción es obligatorio.")
    private double amountTransaction;

    @NotBlank(message = "la descripcion de la transacción es obligatoria.")
    private String descriptionTransaction;

    @NotBlank(message = "el método de pago es obligatorio.")
    private String paymentMethod;

    private LocalDateTime dateRegisterTransaction;

    @NotNull(message = "el estado de la transacción es obligatorio.")
    private boolean statusTransaction;

    public TransaccionDTO() {
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
