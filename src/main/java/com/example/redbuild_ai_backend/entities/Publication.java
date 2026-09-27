package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "publications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String observations;

    @Column(nullable = false)
    private String operationType;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "publisher_user_id", nullable = false)
    private User publisher;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}