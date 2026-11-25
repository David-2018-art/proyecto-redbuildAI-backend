package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(
            max = 120,
            message = "El título no debe superar los 120 caracteres"
    )
    private String title;

    @Size(
            max = 500,
            message = "Las observaciones no deben superar los 500 caracteres"
    )
    private String observations;

    @NotBlank(message = "El tipo de operación es obligatorio")
    @Pattern(
            regexp = "Venta|Donacion",
            message = "El tipo de operación debe ser Venta o Donacion"
    )
    private String operationType;

    private LocalDateTime publicationDate;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "Disponible|Pausada|Finalizada|Cancelada",
            message = "El estado debe ser Disponible, Pausada, Finalizada o Cancelada"
    )
    private String status;

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser mayor que cero")
    private Long productId;

    @NotNull(message = "El ID de la ubicación es obligatorio")
    @Positive(message = "El ID de la ubicación debe ser mayor que cero")
    private Long locationId;

    @NotNull(message = "El ID del publicador es obligatorio")
    @Positive(message = "El ID del publicador debe ser mayor que cero")
    private Long publisherUserId;
}