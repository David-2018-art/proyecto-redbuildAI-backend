package com.example.redbuild_ai_backend.dtos;

public record MaterialAnalysisDTO(
        boolean identificado,
        String nameProduct,
        String descriptionProduct,
        String material,
        String colour
) {
}