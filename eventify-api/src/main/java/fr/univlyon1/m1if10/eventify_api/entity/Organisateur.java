package fr.univlyon1.m1if10.eventify_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "organisateur")
public class Organisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orga")
    private Integer id;

    @Column(name = "utilisateur_id", nullable = false, unique = true)
    private Integer utilisateurId;
}
