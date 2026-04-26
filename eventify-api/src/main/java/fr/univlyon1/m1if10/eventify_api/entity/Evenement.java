package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "evenement")
@Getter
@Setter
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evenement")
    private Integer id;

    @Column(name = "titre", nullable = false, length = 100)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut", nullable = false)
    private LocalDate date_debut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate date_fin;

    @Column(name = "lieu", nullable = false)
    private String lieu;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "poster")
    private String poster;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "nb_places", nullable = false)
    private Integer nb_places;

    @Column(name = "nb_places_vendues", nullable = false)
    private Integer nb_places_vendues = 0;

    @Column(name = "valide", nullable = false)
    private boolean valide = false;

    @Column(name = "orga_id", nullable = false)
    private Integer orgaId;

    @Column(name = "admin_id")
    private Integer adminId;
}