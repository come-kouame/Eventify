package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "evenement")
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evenement")
    private Integer id;

    @Column(name = "titre", nullable = false, length = 100)
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime date_debut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime date_fin;

    @Column(name = "lieu", columnDefinition = "TEXT")
    private String lieu;

    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;

    @Column(name = "poster", columnDefinition = "TEXT")
    private String poster;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "nb_places")
    private Integer nb_places;

    @Column(name = "nb_places_vendues")
    private Integer nb_places_vendues;

    @Column(name = "valide")
    private boolean valide;

    @Column(name = "orga_id", nullable = false)
    private Integer orgaId;

    @Column(name = "admin_id")
    private Integer adminId;
}