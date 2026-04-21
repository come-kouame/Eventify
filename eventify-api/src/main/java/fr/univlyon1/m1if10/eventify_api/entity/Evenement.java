package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "date_event", nullable = false)
    private LocalDate dateEvent; // juste la date pour l'instant

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "orga_id", nullable = false)
    private Integer orgaId;

    @Column(name = "admin_id")
    private Integer adminId;

}