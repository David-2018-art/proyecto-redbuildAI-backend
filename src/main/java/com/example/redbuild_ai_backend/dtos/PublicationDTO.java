package com.example.redbuild_ai_backend.dtos;

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

    private LocalDateTime publicationDate;

    private String status;

    private Long productId;
}