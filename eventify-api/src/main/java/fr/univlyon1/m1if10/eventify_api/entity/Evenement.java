package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evenement")
    private Long id;

    @Column(name = "titre", nullable = false, length = 100)
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_event", nullable = false)
    private LocalDate dateEvent; // juste la date pour l'instant

    @Column(name = "prix", nullable = false, precision = 6, scale = 2)
    private Double prix;

    @Column(name = "orga_id", nullable = false)
    private Integer orgaId;

    @Column(name = "admin_id")
    private Integer adminId;

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