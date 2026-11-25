package com.example.redbuild_ai_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Solicitud de compra o donación de una publicación")
public class TransaccionDTO {

    private Long idTransaccion;

    @NotBlank(message = "El tipo de operación es obligatorio")
    @Pattern(
            regexp = "Venta|Donacion",
            message = "El tipo de operación debe ser Venta o Donacion"
    )
    private String typeTransaction;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0", inclusive = false,
            message = "La cantidad debe ser mayor que cero")
    @Digits(integer = 9, fraction = 3,
            message = "La cantidad admite hasta 9 enteros y 3 decimales")
    private BigDecimal quantityTransaction;

    @NotNull(message = "El precio unitario acordado es obligatorio")
    @DecimalMin(value = "0",
            message = "El precio no puede ser negativo")
    @Digits(integer = 10, fraction = 2,
            message = "El precio admite hasta 10 enteros y 2 decimales")
    private BigDecimal agreedUnitPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal amountTransaction;

    @Size(max = 500, message = "El mensaje admite hasta 500 caracteres")
    private String descriptionTransaction;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dateRegisterTransaction;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime reservationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime closingDate;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "Solicitada|Reservada|Completada|Cancelada|Rechazada",
            message = "El estado debe ser Solicitada, Reservada, "
                    + "Completada, Cancelada o Rechazada"
    )
    private String statusTransaction;

    @NotNull(message = "El ID de la publicación es obligatorio")
    @Positive(message = "El ID de la publicación debe ser mayor que cero")
    private Long idPublication;

    @NotNull(message = "El ID del usuario adquirente es obligatorio")
    @Positive(message = "El ID del usuario debe ser mayor que cero")
    private Long idUser;

    public TransaccionDTO() {
    }

    @AssertTrue(message = "En una donación el precio debe ser cero; "
            + "en una venta debe ser mayor que cero")
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Schema(hidden = true)
    public boolean isPrecioCompatible() {
        if (typeTransaction == null || agreedUnitPrice == null) {
            return true;
        }

        if ("Donacion".equals(typeTransaction)) {
            return agreedUnitPrice.compareTo(BigDecimal.ZERO) == 0;
        }

        if ("Venta".equals(typeTransaction)) {
            return agreedUnitPrice.compareTo(BigDecimal.ZERO) > 0;
        }

        return true;
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

    public void setAmountTransaction(BigDecimal amountTransaction) {
        this.amountTransaction = amountTransaction;
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

    public Long getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Long idPublication) {
        this.idPublication = idPublication;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}