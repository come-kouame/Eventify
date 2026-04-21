package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Evenement {

    // Getters & Setters
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evenement")
    private Integer id;

    @Setter
    @Getter
    @Column(name = "titre", nullable = false, length = 100)
    private String titre;

    @Getter
    @Setter
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Getter
    @Setter
    @Column(name = "date_event", nullable = false)
    private LocalDate dateEvent; // juste la date pour l'instant

    @Getter
    @Setter
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "orga_id", nullable = false)
    private Integer orgaId;

    @Column(name = "admin_id")
    private Integer adminId;

}