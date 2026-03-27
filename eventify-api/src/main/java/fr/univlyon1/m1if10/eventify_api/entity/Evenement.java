package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double prix;

    private LocalDate dateEvent; // juste la date pour l'instant

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public LocalDate getDateEvent() { return dateEvent; }
    public void setDateEvent(LocalDate dateEvent) { this.dateEvent = dateEvent; }
}