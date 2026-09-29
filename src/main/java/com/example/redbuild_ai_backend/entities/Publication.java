package com.example.redbuild_ai_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 500)
    private String observations;

    @Column(nullable = false, length = 20)
    private String operationType;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false, length = 20)
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

    // Constructor vacío
    public Publication() {
    }

    // Constructor con todos los atributos
    public Publication(
            Long id,
            String title,
            String observations,
            String operationType,
            LocalDateTime publicationDate,
            String status,
            Product product,
            User publisher,
            Location location) {

        this.id = id;
        this.title = title;
        this.observations = observations;
        this.operationType = operationType;
        this.publicationDate = publicationDate;
        this.status = status;
        this.product = product;
        this.publisher = publisher;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}