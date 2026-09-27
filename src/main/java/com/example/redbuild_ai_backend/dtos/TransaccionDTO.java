package com.example.redbuild_ai_backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(
        name = "TransaccionDTO",
        description = "Representa la información de una transacción realizada por un usuario.",
        example = "{\"typeTransaction\":\"Pago\",\"amountTransaction\":250000,\"descriptionTransaction\":\"Compra de servicio premium\",\"paymentMethod\":\"Tarjeta de crédito\",\"statusTransaction\":true,\"idUser\":7}"
)
public class TransaccionDTO {

    @Schema(description = "ID único de la transacción.", example = "1")
    private Long idTransaccion;

    @Schema(description = "Tipo de transacción realizada.", example = "Pago")
    @NotBlank(message = "el tipo de transacción es obligatorio.")
    private String typeTransaction;

    @Schema(description = "Monto total de la transacción.", example = "250000")
    @NotNull(message = "el monto de la transacción es obligatorio.")
    private double amountTransaction;

    @Schema(description = "Descripción detallada de la transacción.", example = "Compra de servicio premium")
    @NotBlank(message = "la descripcion de la transacción es obligatoria.")
    private String descriptionTransaction;

    @Schema(description = "Método de pago empleado en la transacción.", example = "Tarjeta de crédito")
    @NotBlank(message = "el método de pago es obligatorio.")
    private String paymentMethod;

    @Schema(description = "Fecha y hora en que se registró la transacción.", example = "2026-09-23T20:30:00")
    private LocalDateTime dateRegisterTransaction;

    @Schema(description = "Estado de la transacción: activa o inactiva.", example = "true")
    @NotNull(message = "el estado de la transacción es obligatorio.")
    private boolean statusTransaction;

    @Schema(description = "ID del usuario que realizó la transacción.", example = "7")
    private Long idUser;

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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}

